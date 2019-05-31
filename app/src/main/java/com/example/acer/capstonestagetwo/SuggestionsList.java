package com.example.acer.capstonestagetwo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.MenuItem;
import android.widget.Toast;

import com.example.acer.capstonestagetwo.Adapters.Adapter;
import com.example.acer.capstonestagetwo.ModelClass.MovieRating;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.marcoscg.materialtoast.MaterialToast;

import java.util.ArrayList;
import java.util.List;

public class SuggestionsList extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databasemovie;
    ProgressDialog dialog;
    List<MovieRating> ratingList;
    String title;
    String releasedate;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions_list);
        recyclerView = findViewById(R.id.recycler);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        databasemovie = FirebaseDatabase.getInstance().getReference("upcoming");
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.Loading_suggestions));
        dialog.setCancelable(false);
        dialog.show();
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        ratingList = new ArrayList<>();
        title = getIntent().getStringExtra("moviename");
        releasedate = getIntent().getStringExtra("rdate");
        Query query = FirebaseDatabase.getInstance().getReference("upcoming").orderByChild("title").equalTo(title);


        query.addListenerForSingleValueEvent(valueEventListener);

        widgetdata();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = NavUtils.getParentActivityIntent(this);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            NavUtils.navigateUpTo(this, intent);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            ratingList.clear();

            for (DataSnapshot workersnapshot : dataSnapshot.getChildren()) {
                MovieRating rating = workersnapshot.getValue(MovieRating.class);
                ratingList.add(rating);

            }
            dialog.dismiss();
            if (ratingList.isEmpty()) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(SuggestionsList.this);
                alertDialog.setTitle(R.string.oops);
                alertDialog.setMessage(R.string.no_suggestions_yet);

                alertDialog.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.cancel();
                    }

                });
                alertDialog.show();
            } else {

                Adapter Adapter = new Adapter(SuggestionsList.this, ratingList);

                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);

                recyclerView.setLayoutManager(new LinearLayoutManager(SuggestionsList.this));
                recyclerView.setAdapter(Adapter);
                Adapter.notifyDataSetChanged();

            }
        }


        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public void widgetdata() {


        SharedPreferences sharedPreferences = getSharedPreferences("preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("redate", releasedate);
        editor.putString("title", title);
        editor.apply();
        Intent intent1 = new Intent(this, NewAppWidget.class);
        intent1.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        int id[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), NewAppWidget.class));
        intent1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, id);
        getApplicationContext().sendBroadcast(intent1);

        MaterialToast.makeText(SuggestionsList.this, getString(R.string.widget_details), R.mipmap.widget, Toast.LENGTH_SHORT).show();


    }


}
