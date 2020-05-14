package utils;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

// this class will be used as Model
@Entity
public class Movie {
    private String title;
    private String overview;
    @PrimaryKey(autoGenerate = false)
    public int id;
    private double vote;
    private String posterUrl;
    @Ignore()
    private Bitmap poster;
    private String date;
    @ColumnInfo(name = "fav")
    private boolean favourite;


    public Movie(String title, String overview, int id, double vote, String posterUrl, String date){
        this.title = title;
        this.overview = overview;
        this.id = id;
        this.vote = vote;
        this.posterUrl = posterUrl;
        this.date = date;
        this.favourite = false;
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
    public boolean getFavourite(){return favourite;}
    public void setFavourite(boolean favourite){this.favourite = favourite;};
}
