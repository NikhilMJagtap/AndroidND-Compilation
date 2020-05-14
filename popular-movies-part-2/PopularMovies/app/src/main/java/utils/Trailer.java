package utils;

public class Trailer {
    String textTrailer;
    String trailerKey;
    String id;
    int movieId;

    public String getTextTrailer() {
        return textTrailer;
    }

    public String getTrailerKey() {
        return trailerKey;
    }

    public String getId() {
        return id;
    }

    public int getMovieId() {
        return movieId;
    }

    Trailer(String textTrailer,
            String trailerKey,
            String id,
            int movieId){
        this.id = id;
        this.movieId = movieId;
        this.trailerKey = trailerKey;
        this.textTrailer = textTrailer;

    }
     String getKey(){
        return trailerKey;
     }
}
