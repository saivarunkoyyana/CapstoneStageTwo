package com.example.acer.capstonestagetwo.ModelClass;

import java.io.Serializable;

public class UpcomingMovie implements Serializable {
    String image;
    String title;
    String id;
    String voteaverage;
    String overview;
    String releasedate;
    String votecount;
    String popularity;
    String backdrop;

    public UpcomingMovie(String image, String title, String id, String voteaverage, String overview, String releasedate, String votecount, String popularity, String backdrop) {
        this.image = image;
        this.title = title;
        this.id = id;
        this.voteaverage = voteaverage;
        this.overview = overview;
        this.releasedate = releasedate;
        this.votecount = votecount;
        this.popularity = popularity;
        this.backdrop = backdrop;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVoteaverage() {
        return voteaverage;
    }

    public void setVoteaverage(String voteaverage) {
        this.voteaverage = voteaverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    public String getVotecount() {
        return votecount;
    }

    public void setVotecount(String votecount) {
        this.votecount = votecount;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }
}
