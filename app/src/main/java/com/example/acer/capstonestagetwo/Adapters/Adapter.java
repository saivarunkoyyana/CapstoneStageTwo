package com.example.acer.capstonestagetwo.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.acer.capstonestagetwo.ModelClass.MovieRating;
import com.example.acer.capstonestagetwo.R;
import com.example.acer.capstonestagetwo.SuggestionsList;

import java.util.List;

 public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    Context context;
    List<MovieRating> ratingList;

    public Adapter(SuggestionsList suggestionsList, List<MovieRating> ratingList) {
        this.context = suggestionsList;
        this.ratingList = ratingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textView.setText(ratingList.get(i).getMoviesuggestion());
    }

    @Override
    public int getItemCount() {
        return ratingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.rating);

        }
    }
}
