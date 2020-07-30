package darshita.com.parentteacherinteraction.details;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Arrays;
import java.util.List;
import darshita.com.parentteacherinteraction.R;


public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.ViewHolder> {



Context context;
List<String> stringList;
     public SubjectsAdapter(Context context, List<String> list) {
      this.context=context;
      this.stringList=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_subjects, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

         String s=stringList.get(position);
         List<String> list= Arrays.asList(s.split(";"));
         holder.subject.setText(list.get(0));
         holder.marks.setText(list.get(1));

      }



    @Override
    public int getItemCount() {
        return stringList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView subject,marks;

        public ViewHolder(View itemView) {
            super(itemView);
            subject=itemView.findViewById(R.id.subjectname);
            marks=itemView.findViewById(R.id.marks);


        }
    }
}
