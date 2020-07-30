package darshita.com.parentteacherinteraction.parent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import darshita.com.parentteacherinteraction.R;
import darshita.com.parentteacherinteraction.events.DisplayEvents;
import darshita.com.parentteacherinteraction.query.QueryActivity;
import darshita.com.parentteacherinteraction.studentdetails.StudentDetails;

import static darshita.com.parentteacherinteraction.utils.Constants.PARENT;
import static darshita.com.parentteacherinteraction.utils.Constants.SENTBY;

public class ParentActivity extends AppCompatActivity {

    Button student, event, query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);
        student = findViewById(R.id.studentdetails);
        event = findViewById(R.id.eventdetails);
        query = findViewById(R.id.query);
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ParentActivity.this, StudentDetails.class).putExtra(SENTBY, PARENT));
            }
        });
        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ParentActivity.this, DisplayEvents.class));
            }
        });
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ParentActivity.this, QueryActivity.class).putExtra(SENTBY, PARENT));
            }
        });
    }
}
