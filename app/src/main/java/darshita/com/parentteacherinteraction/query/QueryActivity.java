package darshita.com.parentteacherinteraction.query;

import android.app.DownloadManager;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.QueryResultPage;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import java.util.ArrayList;
import java.util.List;

import darshita.com.parentteacherinteraction.DatabaseHandler;
import darshita.com.parentteacherinteraction.R;
import darshita.com.parentteacherinteraction.aws.AmazonClientManager;
import darshita.com.parentteacherinteraction.aws.EventsDO;
import darshita.com.parentteacherinteraction.aws.QueryTableDO;

import static darshita.com.parentteacherinteraction.utils.Constants.PARENT;
import static darshita.com.parentteacherinteraction.utils.Constants.SENTBY;

public class QueryActivity extends AppCompatActivity {

QueryTableDO queryTableDO;
RecyclerView recyclerView;
QueryAdapter queryAdapter;
List<QueryTableDO> list=new ArrayList<>();
DynamoDBMapper mapper;
DatabaseHandler db;
String mobile;
     String whos;
    QueryResultPage<QueryTableDO> queryTableDOQueryResultPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AmazonClientManager clientManager=new AmazonClientManager(this);
        AmazonDynamoDBClient ddb = clientManager.ddb();
        mapper = new DynamoDBMapper(ddb);
        db=DatabaseHandler.getInstance(this);
        mobile=db.getUserData().getMobileNumber();
       whos=getIntent().getStringExtra(SENTBY);
        if (whos.equals(PARENT)) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    QueryTableDO queryTableDO = new QueryTableDO();
                    queryTableDO.setMobileNumber(mobile);
                    DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                            .withHashKeyValues(queryTableDO)
                            .withConsistentRead(false);
                    queryTableDOQueryResultPage = mapper.queryPage(QueryTableDO.class, queryExpression);
                    list.clear();
                    list.addAll(queryTableDOQueryResultPage.getResults());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            queryAdapter = new QueryAdapter(QueryActivity.this, list,whos);
                            recyclerView.setAdapter(queryAdapter);
                        }
                    });
                }
            }).start();
        }
        else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression();
                    PaginatedScanList<QueryTableDO> result  = mapper.scan(QueryTableDO.class, dynamoDBScanExpression);
                    list.clear();
                    list.addAll(result);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            queryAdapter = new QueryAdapter(QueryActivity.this, list,whos);
                            recyclerView.setAdapter(queryAdapter);
                        }
                    });
                }
            }).start();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (whos.equals(PARENT))
            menu.getItem(0).setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        showChangeLangDialog();
        return super.onOptionsItemSelected(item);
    }
    public void showChangeLangDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);

        dialogBuilder.setTitle("Query");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, int whichButton) {
               final String uniqueId=System.currentTimeMillis()+"";
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final QueryTableDO queryTableDO=new QueryTableDO();
                        queryTableDO.setMobileNumber(mobile);
                        queryTableDO.setUniqueId(uniqueId);
                        queryTableDO.setQuestion(edt.getText().toString());
                        mapper.save(queryTableDO);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(QueryActivity.this, "submitted", Toast.LENGTH_SHORT).show();
                                list.add(queryTableDO);
                                queryAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        });
                    }
                }).start();



            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

}
