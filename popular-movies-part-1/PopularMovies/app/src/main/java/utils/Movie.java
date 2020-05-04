package utils;

import android.graphics.Bitmap;

public class Movie {
    public String title;
    public String overview;
    public int id;
    public double vote;
    public String posterUrl;
    public Bitmap poster;
    public String date;

    public Movie(String title, String overview, int id, double vote, String posterUrl, String date){
        this.title = title;
        this.overview = overview;
        this.id = id;
        this.vote = vote;
        this.posterUrl = posterUrl;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public int getId() {
        return id;
    }

    public double getVote() {
        return vote;
    }

    public Bitmap getPoster() {
        return poster;
    }
    public String getDate(){
        return date;
    }
    public String getPosterUrl(){
        return posterUrl;
    }
    public void setPoster(Bitmap poster){
        this.poster = poster;
    }
}
