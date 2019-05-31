package com.example.acer.capstonestagetwo.ModelClass;

public class MovieRating {
    String id;
    String moviesuggestion;
    String title;

    public MovieRating() {

    }

    public MovieRating(String id, String moviesuggestion, String title) {
        this.id = id;
        this.moviesuggestion = moviesuggestion;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMoviesuggestion() {
        return moviesuggestion;
    }

    public void setMoviesuggestion(String moviesuggestion) {
        this.moviesuggestion = moviesuggestion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
