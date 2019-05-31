package com.example.acer.capstonestagetwo.UI;

import android.app.AlertDialog;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.capstonestagetwo.InputRange;
import com.example.acer.capstonestagetwo.ModelClass.MovieRating;
import com.example.acer.capstonestagetwo.R;
import com.example.acer.capstonestagetwo.SuggestionsList;
import com.example.acer.capstonestagetwo.ModelClass.UpcomingMovie;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.marcoscg.materialtoast.MaterialToast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    List<UpcomingMovie> upcomingMovieList;
    String image = null;
    String title = null;
    String id = null;
    ImageView imageView, imageViewPosterDetails;
    String position = null;
    EditText suggestion;
    Button suggest;
    String moviesuggestion, releasedate;
    String voteaverage = null;
    TextView movietitlevalue, releasedatevalue, votevalue, summaryvalue, popularityvalue, voteaveragevalue;
    DatabaseReference databasemovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        upcomingMovieList = new ArrayList<>();
        movietitlevalue = findViewById(R.id.movieTitleValue);
        releasedatevalue = findViewById(R.id.movieReleaseDateValue);
        votevalue = findViewById(R.id.movieVoteValue);
        imageViewPosterDetails = findViewById(R.id.imageViewPosterDetails);
        suggest = findViewById(R.id.suggest);
        summaryvalue = findViewById(R.id.movieSummaryValue);
        popularityvalue = findViewById(R.id.movieReviewValue);
        voteaveragevalue = findViewById(R.id.voteaveragevalue);
        Context context = this;
        imageView = findViewById(R.id.image_id);

        databasemovie = FirebaseDatabase.getInstance().getReference("upcoming");


        upcomingMovieList = (List<UpcomingMovie>) getIntent().getSerializableExtra("movielist");
        String position = getIntent().getStringExtra("position");
        title = upcomingMovieList.get(Integer.parseInt(position)).getTitle();
        releasedate = upcomingMovieList.get(Integer.parseInt(position)).getReleasedate();


        Picasso.with(context).load(upcomingMovieList.get(Integer.parseInt(position)).getBackdrop()).into(imageView);
        Picasso.with(context).load(upcomingMovieList.get(Integer.parseInt(position)).getImage()).into(imageViewPosterDetails);
        movietitlevalue.setText(upcomingMovieList.get(Integer.parseInt(position)).getTitle());
        releasedatevalue.setText(upcomingMovieList.get(Integer.parseInt(position)).getReleasedate());
        votevalue.setText(upcomingMovieList.get(Integer.parseInt(position)).getVotecount());
        summaryvalue.setText(upcomingMovieList.get(Integer.parseInt(position)).getOverview());
        popularityvalue.setText(upcomingMovieList.get(Integer.parseInt(position)).getPopularity());
        voteaveragevalue.setText(upcomingMovieList.get(Integer.parseInt(position)).getVoteaverage());

        MaterialToast.makeText(DetailsActivity.this, "" + title, Toast.LENGTH_SHORT).show();

        suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetailsActivity.this);
                alertDialog.setTitle(R.string.scale);
                alertDialog.setMessage(R.string.suggestion);

                final EditText input = new EditText(DetailsActivity.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);

                input.setFilters(new InputFilter[]{new InputRange(1, 10)});


                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);


                alertDialog.setPositiveButton(R.string.done,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                moviesuggestion = input.getText().toString();


                                if (!TextUtils.isEmpty(moviesuggestion)) {
                                    String id = databasemovie.push().getKey();

                                    MovieRating rating = new MovieRating(id, moviesuggestion, title);

                                    databasemovie.child(id).setValue(rating);

                                    MaterialToast.makeText(DetailsActivity.this, getString(R.string.suggestion_added), R.mipmap.added, Toast.LENGTH_SHORT).show();

                                } else {
                                    MaterialToast.makeText(DetailsActivity.this, getString(R.string.suggestion_not_given), R.mipmap.notavailable, Toast.LENGTH_SHORT).show();
                                }
                            }

                        });

                alertDialog.setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.suggestions, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(DetailsActivity.this, SuggestionsList.class);
        intent.putExtra("moviename", title);
        intent.putExtra("rdate", releasedate);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

}
