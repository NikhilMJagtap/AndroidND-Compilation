package network;

import java.util.List;
import java.util.Map;

import model.Article;
import model.ResponseModelForNewsApi;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface NewsService {
    String BASE_URL = "https://newsapi.org/v2/";

    @GET("/v2/top-headlines")
    Call<ResponseModelForNewsApi> getHeadlines(
//            @Query("country") String country,
//            @Query("category") String category,
//            @Query("apiKey") String apiKey
            @QueryMap(encoded = true) Map<String, String> options
    );

    @GET("/v2/everything")
    Call<ResponseModelForNewsApi> getEverything(
//            @Query("apiKey") String apiKey
            @QueryMap(encoded = true) Map<String, String> options
    );
}
