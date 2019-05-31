package com.example.acer.capstonestagetwo.UI;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import android.support.v7.widget.RecyclerView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.acer.capstonestagetwo.Key;
import com.example.acer.capstonestagetwo.Adapters.PosterAdapter;
import com.example.acer.capstonestagetwo.R;
import com.example.acer.capstonestagetwo.ModelClass.UpcomingMovie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PosterActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    static List<UpcomingMovie> movieList;
    RecyclerView recyclerView;
    String image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);
        movieList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(PosterActivity.this);
        recyclerView = findViewById(R.id.recyclerview);

        if (getApplication().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(PosterActivity.this, 2));
        } else if (getApplication().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(PosterActivity.this, 4));
        }





        Myasc myasc = new Myasc();
        myasc.execute();


    }




    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(PosterActivity.this, 4));
        }

    }

    void Loadmovies() {


        String url = Key.APIKEY;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                    try {

                        String title = null;
                        String id = null;
                        String overview = null;
                        String releasedate = null;
                        String votecount = null;
                        String voteaverage = null;
                        String popularity = null;


                        JSONObject root = new JSONObject(response);
                        JSONArray results = root.getJSONArray("results");
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject obj = results.getJSONObject(i);
                            image = "https://image.tmdb.org/t/p/w500" + obj.optString("poster_path");
                            title = obj.optString("title");
                            String backdrop = "https://image.tmdb.org/t/p/w1280" + obj.optString("backdrop_path");
                            id = obj.getString("id");
                            votecount = obj.getString("vote_count");
                            popularity = obj.getString("popularity");
                            voteaverage = obj.getString("vote_average");
                            overview = obj.getString("overview");
                            releasedate = obj.getString("release_date");


                            UpcomingMovie movie = new UpcomingMovie(image, title, id, voteaverage, overview, releasedate, votecount, popularity, backdrop);
                            movieList.add(movie);
                        }

                        PosterAdapter posterAdapter = new PosterAdapter(PosterActivity.this, movieList);
                        recyclerView.setAdapter(posterAdapter);
                        recyclerView.setHasFixedSize(true);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.exit)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    private class Myasc extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {

            ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager
                    .getActiveNetworkInfo();


            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }

        @Override
        protected void onPostExecute(Boolean s) {
            super.onPostExecute(s);
            if (s==true) {
                Loadmovies();
            }
            else{
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(PosterActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                builder.setTitle(R.string.network_connection).setMessage(R.string.check_internet)
                        .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(PosterActivity.this, PosterActivity.class);
                                startActivity(intent);
                            }
                        });
                builder.show();
            }

        }
    }
}

