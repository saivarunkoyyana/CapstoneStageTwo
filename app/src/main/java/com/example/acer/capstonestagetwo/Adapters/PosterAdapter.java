package com.example.acer.capstonestagetwo.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.acer.capstonestagetwo.R;
import com.example.acer.capstonestagetwo.UI.DetailsActivity;
import com.example.acer.capstonestagetwo.UI.PosterActivity;
import com.example.acer.capstonestagetwo.ModelClass.UpcomingMovie;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.ViewHolder> {
    private Context context;
    private List<UpcomingMovie> movieList;

    public PosterAdapter(PosterActivity posterActivity, List<UpcomingMovie> movieList) {
        this.context = posterActivity;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.poster, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Picasso.with(context).load(movieList.get(i).getImage()).into(viewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Intent i = new Intent(context, DetailsActivity.class);
                    i.putExtra("movielist", (Serializable) movieList);
                    i.putExtra("position", position + "");

                    context.startActivity(i);
                }
            });
        }
    }
}
