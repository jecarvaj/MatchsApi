package com.example.jean.matchapi;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private RequestQueue queue;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Match> matchList;
    private Boolean hasPrevious;
    private int numPage;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        initRecycler();
        getNumPages();

    }

    //Primero obtengo el numero de paginas, para extraer los datos desde los ultimos al primero
    //para que queden los mas recientes primero
    private void getNumPages() {
        progressBar.setVisibility(View.VISIBLE);
        String url = "http://futbol.masfanatico.cl/api/u-chile/match/in_competition/transicion2017";
        JsonObjectRequest jsObj=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    numPage = response.getInt("num_pages");
                    getData(); //Ya listo el numero de paginas, obtengo los datos
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
        MySingleton.getInstance(this).addToRequestQueue(jsObj);
    }

    //Obtengo los datos de la api
    private void getData() {
        String url = "http://futbol.masfanatico.cl/api/u-chile/match/in_competition/transicion2017?p=" + numPage;
        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            hasPrevious = response.getBoolean("has_previous"); //para saber si llegue a la primera pagina
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

    //Creo objetos Match a partir del jsonarray y los guardo en una lista
    private void parseData(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            String localName = item.getString("local_team_name_s");
            String visitName = item.getString("visit_team_name_s");
            int localGoals = item.getInt("local_goals_i");
            int visitGoals = item.getInt("visit_goals_i");
            String localImage = item.getString("local_team_image_team-icon_url_s");
            String visitImage = item.getString("visit_team_image_team-icon_url_s");
            String stadiumName = item.getString("stadium_name_s");
            String startTime = item.getString("start_time_dt");

            matchList.add(new Match(localName, visitName, localGoals, visitGoals, localImage, visitImage, stadiumName, startTime));

        }
        //ordeno por fecha para ver los mas recientes primero
        Collections.sort(matchList);
        adapter.notifyDataSetChanged(); //notifico al recyclerview que he actualizado los datos para que los muestre
        progressBar.setVisibility(View.GONE);
    }

    private void initRecycler() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        matchList = new ArrayList<>();
        adapter = new MatchAdapter(matchList, this);
        recyclerView.setAdapter(adapter);

        //Para que identifique al llegar al bottom, si es que quedan paginas por traer llamo a getData again
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                    if (hasPrevious) {
                        numPage--;
                        getData();
                    }
                }
            }
        });
    }

}
