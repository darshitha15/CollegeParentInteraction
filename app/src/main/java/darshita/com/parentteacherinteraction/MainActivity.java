package darshita.com.parentteacherinteraction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import darshita.com.parentteacherinteraction.aws.UserIdpassDO;
import darshita.com.parentteacherinteraction.login.Login;
import darshita.com.parentteacherinteraction.parent.ParentActivity;
import darshita.com.parentteacherinteraction.faculty.TeacherActivity;

import static darshita.com.parentteacherinteraction.utils.Constants.CATEGORY;
import static darshita.com.parentteacherinteraction.utils.Constants.PARENT;
import static darshita.com.parentteacherinteraction.utils.Constants.FACULTY;

public class MainActivity extends AppCompatActivity {

    Button parent,techer;
    FirebaseAuth auth;
    UserIdpassDO userIdpassDO;
    DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parent=findViewById(R.id.parent);
        techer=findViewById(R.id.faculty);
        FirebaseApp.initializeApp(this);
        db=DatabaseHandler.getInstance(this);
        auth = FirebaseAuth.getInstance();
        userIdpassDO=db.getUserData();
        if (userIdpassDO != null) {
            // already signed in

            if (userIdpassDO.getCategory().equals(FACULTY))
                startActivity(new Intent(MainActivity.this, TeacherActivity.class));
            else if (userIdpassDO.getCategory().equals(PARENT))
                startActivity(new Intent(MainActivity.this, ParentActivity.class));
            finish();
        }
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Login.class).putExtra(CATEGORY,PARENT));
                finish();
            }
        });

        techer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Login.class).putExtra(CATEGORY,FACULTY));
                finish();
            }
        });


    }
}
