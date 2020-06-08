package utils;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Step{
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("shortDescription")
    private String shortDescription;
    @Expose
    @SerializedName("description")
    private String description;
    private boolean isVideo = true;
    @Expose
    @SerializedName("videoURL")
    private String videoURL;
    @Expose
    @SerializedName("thumbnailURL")
    private String thumbnailURL;

    public Step(int id, String shortDescription, String description, String videoURL, String thumbnailURL){
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
//        if(!videoURL.equals("")){
//            this.isVideo = true;
//            this.videoURL = videoURL;
//            this.thumbnailURL = null;
//        } else if(thumbnailURL != null || !thumbnailURL.equals("")){
//            this.isVideo = false;
//            this.videoURL = null;
//            this.thumbnailURL = thumbnailURL;
//        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public String getURL(){
//        Log.e("API2", "ASking url " + videoURL);
//        if(this.isVideo)
//            return this.videoURL;
//        else
//            return this.thumbnailURL;
//    }
//
//    public void setURL(String url){
//        if(this.isVideo)
//            this.videoURL = url;
//        else
//            this.thumbnailURL = url;
//    }


    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }
}
