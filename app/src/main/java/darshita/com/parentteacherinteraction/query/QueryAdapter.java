package darshita.com.parentteacherinteraction.query;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.squareup.picasso.Picasso;

import java.util.List;

import darshita.com.parentteacherinteraction.R;
import darshita.com.parentteacherinteraction.aws.AmazonClientManager;
import darshita.com.parentteacherinteraction.aws.EventsDO;
import darshita.com.parentteacherinteraction.aws.QueryTableDO;

import static darshita.com.parentteacherinteraction.utils.Constants.FACULTY;
import static darshita.com.parentteacherinteraction.utils.Constants.PIC;


public class QueryAdapter extends RecyclerView.Adapter<QueryAdapter.ViewHolder> {



Context context;
    List<QueryTableDO> list;
    String who;
     public QueryAdapter(Context context, List<QueryTableDO> list,String who) {
      this.context=context;
      this.list=list;
      this.who=who;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_query, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final QueryTableDO queryTableDO=list.get(position);
        holder.question.setText(queryTableDO.getQuestion());
        if (queryTableDO.getAnswer()!=null)
        {
            holder.answer.setText(queryTableDO.getAnswer());
        }
        else {
            if (who.equals(FACULTY)) {
                holder.editLayout.setVisibility(View.VISIBLE);

            }

            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!holder.edit.getText().toString().equals(""))
                    {
                        holder.editLayout.setVisibility(View.GONE);
                        queryTableDO.setAnswer(holder.edit.getText().toString());
                        holder.answer.setText(holder.edit.getText().toString());
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                AmazonClientManager clientManager=new AmazonClientManager(context);
                                 AmazonDynamoDBClient ddb = clientManager.ddb();
                                 DynamoDBMapper mapper = new DynamoDBMapper(ddb);
                                 mapper.save(queryTableDO);

                            }
                        }).start();
                    }
                    else Toast.makeText(context, "cannot me empty", Toast.LENGTH_SHORT).show();
                }
            });
        }
      }



    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView question,answer;
       EditText edit;
       Button button;
       LinearLayout editLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            question=itemView.findViewById(R.id.question);
            answer=itemView.findViewById(R.id.answer);
            editLayout=itemView.findViewById(R.id.editLayout);
            edit=itemView.findViewById(R.id.edit);
            button=itemView.findViewById(R.id.reply);



        }
    }
}
