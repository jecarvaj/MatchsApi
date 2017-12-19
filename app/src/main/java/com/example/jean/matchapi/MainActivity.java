package com.example.jean.matchapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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
        queue= Volley.newRequestQueue(this);
        String url = "http://futbol.masfanatico.cl/api/u-chile/match/in_competition/transicion2017";
        recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        matchList=new ArrayList<>();
        recyclerView.setLayoutManager(layoutManager);



        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray=response.getJSONArray("items");
                            Log.d("APIJSON","RESPONSE:> "+jsonArray.length());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Log.d("APIJSON","DENTRO DE FOR:> "+i);
                                JSONObject item=jsonArray.getJSONObject(i);
                                String localName=item.getString("local_team_name_s");
                                String visitName=item.getString("visit_team_name_s");
                                int localGoals=item.getInt("local_goals_i");
                                int visitGoals=item.getInt("visit_goals_i");
                                String localImage=item.getString("local_team_image_team-icon_url_s");
                                String visitImage=item.getString("visit_team_image_team-icon_url_s");
                                String stadiumName=item.getString("stadium_name_s");
                                String startTime=item.getString("start_time_dt");
                                Match match=new Match(localName, visitName, localGoals, visitGoals,
                                        localImage, visitImage, stadiumName, startTime);
                                matchList.add(match);
                            }

                            adapter=new MatchAdapter(matchList);

                            recyclerView.setAdapter(adapter);
                            Log.d("APIJSON","TERMINA ADAPTER");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("APIJSON","PROBLEMAS CON LA GET ");

                    }
                });
        queue.add(jsObjRequest);
        Log.d("APIJSON","COMIENZO ADAPTER");



    }


}
