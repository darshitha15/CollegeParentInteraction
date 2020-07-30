package darshita.com.parentteacherinteraction.details;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import darshita.com.parentteacherinteraction.DatabaseHandler;
import darshita.com.parentteacherinteraction.R;
import darshita.com.parentteacherinteraction.aws.AmazonClientManager;
import darshita.com.parentteacherinteraction.aws.StudentDetailsDO;
import darshita.com.parentteacherinteraction.aws.TotalDetailsSubjectsDO;

import static darshita.com.parentteacherinteraction.utils.Constants.WHAT;

public class DisplayStudDetails extends AppCompatActivity {
    DynamoDBMapper mapper;
    TextView name,rollno,attendance,percentage;
    RecyclerView recyclerView;
    String mob,what;
    DatabaseHandler db;
    TotalDetailsSubjectsDO totalDetailsSubjectsDO;
    SubjectsAdapter subjectsAdapter;
    List<String> stringList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_stud_details);
        name=findViewById(R.id.name);
        rollno=findViewById(R.id.rollno);
        attendance=findViewById(R.id.attendance);
        percentage=findViewById(R.id.percentage);
        recyclerView=findViewById(R.id.recyclerView);
        subjectsAdapter=new SubjectsAdapter(this,stringList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(subjectsAdapter);
        db=DatabaseHandler.getInstance(this);
        AmazonClientManager clientManager=new AmazonClientManager(this);
        AmazonDynamoDBClient ddb = clientManager.ddb();
        mapper = new DynamoDBMapper(ddb);
        mob=db.getUserData().getMobileNumber() ;
        what=getIntent().getStringExtra(WHAT);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                  totalDetailsSubjectsDO=  mapper.load(TotalDetailsSubjectsDO.class,mob,what);
                  if (totalDetailsSubjectsDO!=null)
                  {
                      runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              name.setText(totalDetailsSubjectsDO.getName());
                              rollno.setText(totalDetailsSubjectsDO.getRollno());
                              attendance.setText(totalDetailsSubjectsDO.getAttendance());
                              percentage.setText(totalDetailsSubjectsDO.getPercentage());
                              stringList.clear();
                              stringList.addAll(totalDetailsSubjectsDO.getSubjects());
                              subjectsAdapter.notifyDataSetChanged();
                          }
                      });
                  }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
