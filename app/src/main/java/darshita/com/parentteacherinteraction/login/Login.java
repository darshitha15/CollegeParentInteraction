package darshita.com.parentteacherinteraction.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.GetEndpointAttributesRequest;
import com.amazonaws.services.sns.model.GetEndpointAttributesResult;
import com.amazonaws.services.sns.model.SetEndpointAttributesRequest;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import darshita.com.parentteacherinteraction.DatabaseHandler;
import darshita.com.parentteacherinteraction.R;
import darshita.com.parentteacherinteraction.aws.AmazonClientManager;
import darshita.com.parentteacherinteraction.aws.UserIdpassDO;
import darshita.com.parentteacherinteraction.parent.ParentActivity;
import darshita.com.parentteacherinteraction.profilesetup.ProfileSetup;
import darshita.com.parentteacherinteraction.faculty.TeacherActivity;

import static darshita.com.parentteacherinteraction.utils.Constants.PARENT;
import static darshita.com.parentteacherinteraction.utils.Constants.FACULTY;

public class Login extends AppCompatActivity {

    EditText mobile,pass;
    TextView signupText;
    Button login;
    AmazonSNSClient pushClient;
    String arn;
    String platformApplicationArn;
    String pushNotificationRegId;
    boolean updateNeeded = false;
   FirebaseUser user;
   FirebaseAuth auth;
    private static final int RC_SIGN_IN = 123;
    ProgressDialog Asycdialog ;
    DynamoDBMapper mapper;
    DatabaseHandler db;
    UserIdpassDO userIdpassDO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);
        mobile=findViewById(R.id.inputmobile);
        pass=findViewById(R.id.inputpassword);
        login=findViewById(R.id.login);
        signupText=findViewById(R.id.signuptext);
        Asycdialog = new ProgressDialog(this);
        AmazonClientManager clientManager=new AmazonClientManager(this);
        AmazonDynamoDBClient ddb = clientManager.ddb();
        mapper = new DynamoDBMapper(ddb);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db=DatabaseHandler.getInstance(this);
        pushClient = new AmazonSNSClient(clientManager.getCredentials());
        platformApplicationArn = "arn:aws:sns:us-east-1:491083515657:app/GCM/ParentTeacher";
        pushNotificationRegId = FirebaseInstanceId.getInstance().getToken();
        pushClient.setRegion(Region.getRegion(Regions.US_EAST_1));

        if (auth.getCurrentUser() != null) {
            // already signed in
            userIdpassDO=db.getUserData();
            if (userIdpassDO.getCategory().equals(FACULTY))
                startActivity(new Intent(Login.this, TeacherActivity.class));
            else if (userIdpassDO.getCategory().equals(PARENT))
                startActivity(new Intent(Login.this, ParentActivity.class));
            finish();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mob=mobile.getText().toString();
                String pa=pass.getText().toString();
                if (mob.isEmpty()||pa.isEmpty())
                {
                    Toast.makeText(Login.this, "Enter valid details", Toast.LENGTH_SHORT).show();
                }
                else login(mob,pa);

            }
        });
        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(
                                        Arrays.asList(
                                                new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build()
                                        ))
                                .setTheme(R.style.AppTheme)
                                .build(),
                        RC_SIGN_IN);
            }
        });



    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            // Successfully signed in
            if (resultCode == ResultCodes.OK) {
                startActivity(new Intent(Login.this, ProfileSetup.class));

                return;
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    Log.e("Login", "Login canceled by User");
                    return;
                }
                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Log.e("Login", "No Internet Connection");
                    return;
                }
                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Log.e("Login", "Unknown Error");
                    return;
                }
            }
            Log.e("Login", "Unknown sign in response");
        }
    }


    void login(final String mobile, final String password)
    {

        new Task1(mobile,password).execute();

    }
    class Task1 extends AsyncTask<Void,Void,Void>
    {
        String mobile,  password;
        public Task1(final String mobile, final String password) {
            super();
            this.mobile=mobile;
            this.password=password;
        }

        boolean ex=false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Asycdialog.setMessage("please wait...Loading your profile...");
            Asycdialog.setCanceledOnTouchOutside(false);
            Asycdialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Asycdialog.dismiss();
            if (!ex) {
                db.deleteUser();
                db.addUser(userIdpassDO);
                auth.signInAnonymously();
                if (userIdpassDO.getCategory().equals(FACULTY))
                    startActivity(new Intent(Login.this, TeacherActivity.class));
                else if (userIdpassDO.getCategory().equals(PARENT))
                    startActivity(new Intent(Login.this, ParentActivity.class));

                finish();
            }
            else Toast.makeText(Login.this, "incorrect Password", Toast.LENGTH_SHORT).show();
            login.setEnabled(true);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userIdpassDO = mapper.load(UserIdpassDO.class, mobile);
            if (userIdpassDO != null) {
                if (userIdpassDO.getPassword().equals(password))
                {
                    arn=userIdpassDO.getGcmid();

                    try {
                        GetEndpointAttributesRequest geaReq = new GetEndpointAttributesRequest()
                                .withEndpointArn(arn);
                        GetEndpointAttributesResult geaRes =
                                pushClient.getEndpointAttributes(geaReq);
                        updateNeeded = !geaRes.getAttributes().get("Token").equals(pushNotificationRegId) || !geaRes.getAttributes().get("Enabled").equalsIgnoreCase("true");
                        if (updateNeeded) {
                            if (pushNotificationRegId.equals(""))
                            {
                                pushNotificationRegId = FirebaseInstanceId.getInstance().getToken();
                            }
                            Map attribs = new HashMap();
                            attribs.put("Token", pushNotificationRegId);
                            attribs.put("Enabled", "true");
                            SetEndpointAttributesRequest saeReq = new SetEndpointAttributesRequest()
                                    .withEndpointArn(arn)
                                    .withAttributes(attribs);
                            pushClient.setEndpointAttributes(saeReq);

                        }
                    }
                    catch(Exception nfe){
                        nfe.printStackTrace();
                    }
                }
                else ex=true;

            }
            else ex=true;
            return null;
        }
    }


}
