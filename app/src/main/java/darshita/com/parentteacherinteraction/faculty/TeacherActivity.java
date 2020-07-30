package darshita.com.parentteacherinteraction.faculty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import darshita.com.parentteacherinteraction.R;
import darshita.com.parentteacherinteraction.aws.QueryTableDO;
import darshita.com.parentteacherinteraction.events.DisplayEvents;
import darshita.com.parentteacherinteraction.events.NewEvent;
import darshita.com.parentteacherinteraction.parent.ParentActivity;
import darshita.com.parentteacherinteraction.query.QueryActivity;
import darshita.com.parentteacherinteraction.studentdetails.StudentDetails;

import static darshita.com.parentteacherinteraction.utils.Constants.PARENT;
import static darshita.com.parentteacherinteraction.utils.Constants.SENTBY;
import static darshita.com.parentteacherinteraction.utils.Constants.FACULTY;

public class TeacherActivity extends AppCompatActivity {
    Button student,event,query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        student=findViewById(R.id.studentdetails);
        event=findViewById(R.id.eventdetails);
        query=findViewById(R.id.query);
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherActivity.this, StudentDetails.class).putExtra(SENTBY,FACULTY));
            }
        });
        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherActivity.this, NewEvent.class));
            }
        });
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherActivity.this, QueryActivity.class).putExtra(SENTBY,FACULTY));
            }
        });
    }
}
