package com.example.jean.matchapi;

import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Jean on 18-12-2017.
 */

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchViewHolder> {

    private ArrayList<Match> itemList;
    private ImageLoader queue;
    private Context context;
    private Locale spanish = new Locale("es", "ES");

    public static class MatchViewHolder extends RecyclerView.ViewHolder {

        public TextView tvLocalName, tvVisitName;
        public TextView tvGoals, tvDate, tvStadium;
        public NetworkImageView imgLocal, imgVisit;
        public ImageButton imgButton;


        public MatchViewHolder(View itemView) {
            super(itemView);
            tvLocalName = (TextView) itemView.findViewById(R.id.tvLocalName);
            tvVisitName = (TextView) itemView.findViewById(R.id.tvVisitName);
            tvGoals = (TextView) itemView.findViewById(R.id.tvGoals);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvStadium=(TextView) itemView.findViewById(R.id.tvStadium);
            imgLocal = (NetworkImageView) itemView.findViewById(R.id.imgLocal);
            imgVisit = (NetworkImageView) itemView.findViewById(R.id.imgVisit);
            imgButton = (ImageButton) itemView.findViewById(R.id.btnCalendar);
        }
    }

    public MatchAdapter(ArrayList<Match> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public MatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_item, parent, false);
        MatchViewHolder viewHolder = new MatchViewHolder(layoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MatchViewHolder holder, int position) {
        queue = MySingleton.getInstance(this.context).getImageLoader();
        final Match currentMatch = itemList.get(position);
        //Transformo la fecha para mostrar en pantalla
        SimpleDateFormat sdf = new SimpleDateFormat(" EE dd 'de' MMM ' - ' HH:mm'hrs'", spanish);
        String startTime=sdf.format(currentMatch.getStartTime());

        holder.tvDate.setText(startTime);
        holder.tvStadium.setText(currentMatch.getStadiumName());
        holder.tvLocalName.setText(currentMatch.getLocalName());
        holder.tvVisitName.setText(currentMatch.getVisitName());
        holder.imgLocal.setImageUrl(currentMatch.getLocalImage(), queue);
        holder.imgVisit.setImageUrl(currentMatch.getVisitImage(), queue);
        holder.tvGoals.setText(currentMatch.getLocalGoals() + " - " + currentMatch.getVisitGoals());

        holder.imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Abro calendario y paso datos para guardar evento
                Intent i = new Intent(Intent.ACTION_EDIT);
                i.setType("vnd.android.cursor.item/event");
                i.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, currentMatch.getStartTime().getTime());
                i.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, currentMatch.getStartTime().getTime() + 60 * 60 * 2000);
                i.putExtra(CalendarContract.Events.EVENT_LOCATION, currentMatch.getStadiumName());
                i.putExtra(CalendarContract.Events.TITLE, "Partido "+currentMatch.getLocalName()+" V/S "+currentMatch.getVisitName());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}
