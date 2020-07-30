package darshita.com.parentteacherinteraction.studentdetails;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import darshita.com.parentteacherinteraction.R;
import darshita.com.parentteacherinteraction.details.DisplayStudDetails;
import darshita.com.parentteacherinteraction.details.EditDetails;

import static darshita.com.parentteacherinteraction.utils.Constants.EXTERNALS;
import static darshita.com.parentteacherinteraction.utils.Constants.MID1;
import static darshita.com.parentteacherinteraction.utils.Constants.MID2;
import static darshita.com.parentteacherinteraction.utils.Constants.SENTBY;
import static darshita.com.parentteacherinteraction.utils.Constants.FACULTY;
import static darshita.com.parentteacherinteraction.utils.Constants.WHAT;
import static darshita.com.parentteacherinteraction.utils.Constants.YEAR1;
import static darshita.com.parentteacherinteraction.utils.Constants.YEAR2;
import static darshita.com.parentteacherinteraction.utils.Constants.YEAR3;
import static darshita.com.parentteacherinteraction.utils.Constants.YEAR4;

public class StudentDetails extends AppCompatActivity {

    Button year1,year2,year3,year4,mid1,mid2,externals;
    String year,mid,sentBy;

    LinearLayout midLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        year1=findViewById(R.id.year1);
        year2=findViewById(R.id.year2);
        year3=findViewById(R.id.year3);
        year4=findViewById(R.id.year4);
        mid1=findViewById(R.id.mid1);
        mid2=findViewById(R.id.mid2);
        externals=findViewById(R.id.externals);
        midLayout=findViewById(R.id.midlayout);
        sentBy=getIntent().getStringExtra(SENTBY);

    }
    void yearClick(View view)
    {

        switch (view.getId())
        {
            case R.id.year1:year=YEAR1; break;
            case R.id.year2:year=YEAR2; break;
            case R.id.year3:year=YEAR3; break;
            case R.id.year4:year=YEAR4; break;
        }
        midLayout.setVisibility(View.VISIBLE);
    }
    void midClick(View view){
        switch (view.getId())
        {
            case R.id.mid1:mid=MID1;

            if (sentBy.equals(FACULTY))
            {
                startActivity(new Intent(StudentDetails.this, EditDetails.class).putExtra(WHAT,year+mid));

            }
            else startActivity(new Intent(StudentDetails.this, DisplayStudDetails.class).putExtra(WHAT,year+mid));

            break;
            case R.id.mid2:mid=MID2;
                if (sentBy.equals(FACULTY))
                {
                    startActivity(new Intent(StudentDetails.this, EditDetails.class).putExtra(WHAT,year+mid));

                }
                else startActivity(new Intent(StudentDetails.this, DisplayStudDetails.class).putExtra(WHAT,year+mid));

            break;
            case R.id.externals:mid=EXTERNALS;
                if (sentBy.equals(FACULTY))
                {
                    startActivity(new Intent(StudentDetails.this, EditDetails.class).putExtra(WHAT,year+mid));

                }
                else startActivity(new Intent(StudentDetails.this, DisplayStudDetails.class).putExtra(WHAT,year+mid));

                break;

        }
    }

}
