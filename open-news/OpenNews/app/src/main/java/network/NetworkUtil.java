package network;
import java.util.Map;
import model.ResponseModelForNewsApi;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtil {

    private static Retrofit retrofit;

    public static Call<ResponseModelForNewsApi> loadNews(Map<String, String> options){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(NewsService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        NewsService newsService = retrofit.create(NewsService.class);
        return newsService.getHeadlines(options);
    }

    public static Call<ResponseModelForNewsApi> loadSpecifics(Map<String, String> options){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(NewsService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        NewsService newsService = retrofit.create(NewsService.class);
        return newsService.getEverything(options);
    }

}
