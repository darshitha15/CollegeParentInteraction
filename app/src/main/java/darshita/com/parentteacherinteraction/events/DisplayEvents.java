package darshita.com.parentteacherinteraction.events;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import java.util.ArrayList;
import java.util.List;

import darshita.com.parentteacherinteraction.R;
import darshita.com.parentteacherinteraction.aws.AmazonClientManager;
import darshita.com.parentteacherinteraction.aws.EventsDO;

public class DisplayEvents extends AppCompatActivity {

RecyclerView recyclerView;
EventAdapter eventAdapter;
List<EventsDO> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_events);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new Thread(new Runnable() {
            @Override
            public void run() {
                AmazonClientManager clientManager=new AmazonClientManager(DisplayEvents.this);
                AmazonDynamoDBClient ddb = clientManager.ddb();
                DynamoDBMapper  mapper = new DynamoDBMapper(ddb);
                DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression();
                PaginatedScanList<EventsDO> result  = mapper.scan(EventsDO.class, dynamoDBScanExpression);
                list.clear();
                list.addAll(result);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        eventAdapter=new EventAdapter(DisplayEvents.this,list);
                        recyclerView.setAdapter(eventAdapter);

                    }
                });
            }
        }).start();
    }
}
