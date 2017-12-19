package com.example.jean.matchapi;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jean on 18-12-2017.
 */

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchViewHolder> {

    private ArrayList<Match> itemList;

    public static class MatchViewHolder extends RecyclerView.ViewHolder{

        public TextView localTeam;
        public MatchViewHolder(View itemView) {
            super(itemView);
            localTeam= (TextView) itemView.findViewById(R.id.tvLocalTeam);
        }
    }

    public MatchAdapter(ArrayList<Match> itemList){
        this.itemList=itemList;
    }

    @Override
    public MatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView= LayoutInflater.from(parent.getContext()).inflate(R.layout.match_item,parent, false);
        MatchViewHolder viewHolder=new MatchViewHolder(layoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MatchViewHolder holder, int position) {
        Match currentMatch=itemList.get(position);

        holder.localTeam.setText(currentMatch.getLocalName());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}
