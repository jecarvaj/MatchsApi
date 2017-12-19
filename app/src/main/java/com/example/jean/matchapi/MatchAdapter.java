package com.example.jean.matchapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

/**
 * Created by Jean on 18-12-2017.
 */

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchViewHolder> {

    private ArrayList<Match> itemList;
    private ImageLoader queue;
    private Context context;

    public static class MatchViewHolder extends RecyclerView.ViewHolder{

        public TextView tvLocalName, tvVisitName;
        public TextView tvGoals, tvDateAndStadium;
        public NetworkImageView imgLocal, imgVisit;

        public MatchViewHolder(View itemView) {
            super(itemView);
            tvLocalName= (TextView) itemView.findViewById(R.id.tvLocalName);
            tvVisitName= (TextView) itemView.findViewById(R.id.tvVisitName);
            tvGoals=(TextView) itemView.findViewById(R.id.tvGoals);
            tvDateAndStadium=(TextView) itemView.findViewById(R.id.tvDateAndStadium);
            imgLocal=(NetworkImageView) itemView.findViewById(R.id.imgLocal);
            imgVisit=(NetworkImageView) itemView.findViewById(R.id.imgVisit);
        }
    }

    public MatchAdapter(ArrayList<Match> itemList, Context context){
        this.itemList=itemList;
        this.context=context;
    }

    @Override
    public MatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView= LayoutInflater.from(parent.getContext()).inflate(R.layout.match_item,parent, false);
        MatchViewHolder viewHolder=new MatchViewHolder(layoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MatchViewHolder holder, int position) {
        queue = MySingleton.getInstance(this.context).getImageLoader();

        Match currentMatch=itemList.get(position);

        holder.tvLocalName.setText(currentMatch.getLocalName());
        holder.tvVisitName.setText(currentMatch.getVisitName());
        holder.imgLocal.setImageUrl(currentMatch.getLocalImage(), queue);
        holder.imgVisit.setImageUrl(currentMatch.getVisitImage(), queue);
        holder.tvGoals.setText(currentMatch.getLocalGoals()+" - "+currentMatch.getVisitGoals());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}
