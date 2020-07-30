package darshita.com.parentteacherinteraction.profilesetup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreatePlatformEndpointRequest;
import com.amazonaws.services.sns.model.CreatePlatformEndpointResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import darshita.com.parentteacherinteraction.DatabaseHandler;
import darshita.com.parentteacherinteraction.R;
import darshita.com.parentteacherinteraction.aws.AmazonClientManager;
import darshita.com.parentteacherinteraction.aws.UserIdpassDO;
import darshita.com.parentteacherinteraction.parent.ParentActivity;
import darshita.com.parentteacherinteraction.faculty.TeacherActivity;

import static darshita.com.parentteacherinteraction.utils.Constants.PARENT;
import static darshita.com.parentteacherinteraction.utils.Constants.FACULTY;

public class ProfileSetup extends AppCompatActivity {
    EditText name, password, confirmpassword;
    Button signup;
    FirebaseAuth auth;
    DatabaseHandler db;
    DynamoDBMapper mapper;
    FirebaseUser user;
    String userName,finalPass;
    String platformApplicationArn="arn:aws:sns:us-east-1:491083515657:app/GCM/ParentTeacher";
    AmazonSNSClient pushClient;
    String pushNotificationRegId;
    String arn;
    String role=PARENT;
    RadioButton parent,faculty;
    ProgressDialog Asycdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup);

        password = findViewById(R.id.input_password);
        confirmpassword = findViewById(R.id.input_password_again);
        signup = findViewById(R.id.signup);
        auth = FirebaseAuth.getInstance();
        Asycdialog = new ProgressDialog(ProfileSetup.this);
        db = DatabaseHandler.getInstance(this);
        pushNotificationRegId = FirebaseInstanceId.getInstance().getToken();
        AmazonClientManager clientManager = new AmazonClientManager(this);
        AmazonDynamoDBClient ddb = clientManager.ddb();
        mapper = new DynamoDBMapper(ddb);
        pushClient = new AmazonSNSClient(clientManager.getCredentials());
        pushClient.setRegion(Region.getRegion(Regions.US_EAST_1));
        user = auth.getCurrentUser();
        parent=findViewById(R.id.radioparent);
        faculty=findViewById(R.id.radioteacher);
     
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                role=PARENT;
            }
        });
        faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                role=FACULTY;
            }
        });
       
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password1 = password.getText().toString();
                String  password2 = confirmpassword.getText().toString();
                signup.setEnabled(false);
                if (password1.equals(password2)) {
                    finalPass=password1;
                    register();
                }
                else {
                    signup.setEnabled(true);
                    confirmpassword.setError("passwords does not match");
                }
            }
        });
    }
    void register()
    {
        final String mobile=user.getPhoneNumber().substring(3);
        new AsyncTask<Void,Void,Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Asycdialog.setMessage("please wait...setting up...");
                Asycdialog.setCanceledOnTouchOutside(false);
                Asycdialog.show();
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                signup.setEnabled(true);
                Asycdialog.dismiss();
                auth.signInAnonymously();
                UserIdpassDO userIdpassDO=db.getUserData();
                if (userIdpassDO.getCategory().equals(PARENT))
                    startActivity(new Intent(ProfileSetup.this, ParentActivity.class));
                else if (userIdpassDO.getCategory().equals(FACULTY))
                    startActivity(new Intent(ProfileSetup.this, TeacherActivity.class));

                finish();
            }

            @Override
            protected Void doInBackground(Void... voids) {

                String customPushData = mobile ;
                CreatePlatformEndpointRequest platformEndpointRequest = new CreatePlatformEndpointRequest();
                platformEndpointRequest.setCustomUserData(customPushData);
                platformEndpointRequest.setToken(pushNotificationRegId);
                platformEndpointRequest.setPlatformApplicationArn(platformApplicationArn);
                CreatePlatformEndpointResult result = pushClient.createPlatformEndpoint(platformEndpointRequest);
                arn = result.getEndpointArn();
                UserIdpassDO userIdpassDO=new UserIdpassDO();
                userIdpassDO.setPassword(finalPass);
                userIdpassDO.setMobileNumber(mobile);
                userIdpassDO.setCategory(role);
                userIdpassDO.setGcmid(arn);
                mapper.save(userIdpassDO);
                db.deleteUser();
                db.addUser(userIdpassDO);
                return null;
            }
        }.execute();
    }



}

