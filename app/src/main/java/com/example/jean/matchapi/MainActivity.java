package com.example.jean.matchapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private RequestQueue queue;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Match> matchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        matchList=new ArrayList<>();


        getData();
    }

    private void getData() {
        queue = MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();

        String url = "http://futbol.masfanatico.cl/api/u-chile/match/in_competition/transicion2017";

        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("items");
                            parseData(jsonArray);//Funcion para crear objetos "Match" a partir de json data
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
    }

    private void parseData(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject item=jsonArray.getJSONObject(i);
            String localName=item.getString("local_team_name_s");
            String visitName=item.getString("visit_team_name_s");
            int localGoals=item.getInt("local_goals_i");
            int visitGoals=item.getInt("visit_goals_i");
            String localImage=item.getString("local_team_image_team-icon_url_s");
            String visitImage=item.getString("visit_team_image_team-icon_url_s");
            String stadiumName=item.getString("stadium_name_s");
            String startTime=item.getString("start_time_dt");

            matchList.add(new Match(localName, visitName, localGoals, visitGoals, localImage, visitImage, stadiumName, startTime));
        }
        initRecycler();
    }

    private void initRecycler() {
        recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new MatchAdapter(matchList, this);
        recyclerView.setAdapter(adapter);
    }


}
