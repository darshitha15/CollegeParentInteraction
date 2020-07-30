package darshita.com.parentteacherinteraction.details;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import java.util.ArrayList;
import java.util.List;

import darshita.com.parentteacherinteraction.DatabaseHandler;
import darshita.com.parentteacherinteraction.R;
import darshita.com.parentteacherinteraction.aws.AmazonClientManager;
import darshita.com.parentteacherinteraction.aws.TotalDetailsSubjectsDO;

import static darshita.com.parentteacherinteraction.utils.Constants.WHAT;

public class EditDetails extends AppCompatActivity {

    DynamoDBMapper mapper;
    EditText name,rollno,attendance,percentage,mobile;
    RecyclerView recyclerView;
    String mob,what;
    Button submit;
    TotalDetailsSubjectsDO totalDetailsSubjectsDO;
    SubjectsAdapterEdit subjectsAdapterEdit;
    List<String> stringList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);
        name=findViewById(R.id.name);
        rollno=findViewById(R.id.rollno);
        attendance=findViewById(R.id.attendance);
        percentage=findViewById(R.id.percentage);
        mobile=findViewById(R.id.mobile);
        recyclerView=findViewById(R.id.recyclerView);
        submit=findViewById(R.id.submit) ;
        subjectsAdapterEdit=new SubjectsAdapterEdit(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(subjectsAdapterEdit);
        AmazonClientManager clientManager=new AmazonClientManager(this);
        AmazonDynamoDBClient ddb = clientManager.ddb();
        mapper = new DynamoDBMapper(ddb);
        what=getIntent().getStringExtra(WHAT);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n=name.getText().toString();
                String a=attendance.getText().toString();
                String r=rollno.getText().toString();
                mob=mobile.getText().toString();
                String p=percentage.getText().toString();
                if (n.isEmpty()||a.isEmpty()||r.isEmpty()||mob.isEmpty()||p.isEmpty())
                {
                    Toast.makeText(EditDetails.this, "enter valid details", Toast.LENGTH_SHORT).show();
                }
                else {
                    submit.setEnabled(false);
                    List<String> subjects = subjectsAdapterEdit.getDetails();
                    totalDetailsSubjectsDO = new TotalDetailsSubjectsDO();
                    totalDetailsSubjectsDO.setAttendance(a);
                    totalDetailsSubjectsDO.setName(n);
                    totalDetailsSubjectsDO.setMobileNumber(mob);
                    totalDetailsSubjectsDO.setPercentage(p);
                    totalDetailsSubjectsDO.setRollno(r);
                    totalDetailsSubjectsDO.setSubjects(subjects);
                    totalDetailsSubjectsDO.setUniqueId(what);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mapper.save(totalDetailsSubjectsDO);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(EditDetails.this, "submitted sccessfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });

                        }
                    }).start();
                }

            }
        });
    }
}
