package darshita.com.parentteacherinteraction.events;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import darshita.com.parentteacherinteraction.R;
import darshita.com.parentteacherinteraction.aws.EventsDO;

import static darshita.com.parentteacherinteraction.utils.Constants.PIC;


public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {



Context context;
    List<EventsDO> list;
     public EventAdapter(Context context, List<EventsDO> list) {
      this.context=context;
      this.list=list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_event, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        EventsDO eventsDO=list.get(position);
        holder.event.setText(eventsDO.getEventDetails());
        Picasso.get().load(PIC+eventsDO.getImageUrl()).into(holder.image);
      }



    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView event;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            event=itemView.findViewById(R.id.event);
            image=itemView.findViewById(R.id.eventimage);



        }
    }
}
