package darshita.com.parentteacherinteraction.details;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import darshita.com.parentteacherinteraction.R;


public class SubjectsAdapterEdit extends RecyclerView.Adapter<SubjectsAdapterEdit.ViewHolder> {



Context context;
int count=1;
HashMap<String ,String> hashMap=new HashMap<>();
     public SubjectsAdapterEdit(Context context) {
      this.context=context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_subjectedit, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {



         holder.add.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 final String name=holder.subject.getText().toString();
                 final String marks=holder.marks.getText().toString();

                 if (name.isEmpty()||marks.isEmpty())
                 {
                     Toast.makeText(context, "enter valid details", Toast.LENGTH_SHORT).show();
                 }
                 else {
                     hashMap.put(name,marks);
                     holder.add.setVisibility(View.GONE);
                     count += 1;
                     notifyItemInserted(count);
                 }
             }
         });
      }

    List<String> getDetails()
    {
        List<String> list=new ArrayList<>();
        Set keys = hashMap.keySet();
        for (Iterator i = keys.iterator(); i.hasNext(); ) {
            String key = (String) i.next();
            String value = (String) hashMap.get(key);
            String finalS=key+";"+value;
            list.add(finalS);
        }
        return list;
    }

    @Override
    public int getItemCount() {
        return count;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView subject,marks;
        Button add;

        public ViewHolder(View itemView) {
            super(itemView);
            subject=itemView.findViewById(R.id.subjectname);
            marks=itemView.findViewById(R.id.marks);
            add=itemView.findViewById(R.id.add);


        }
    }
}
