package utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.popularmovies.MovieRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class MovieDBUtils {

    private static MovieDBUtils instance;
    MovieRecyclerViewAdapter adapter;

    private MovieDBUtils(){

    }

    public static MovieDBUtils getInstance(){
        if(instance == null)
            instance = new MovieDBUtils();
        return instance;
    }

    public void start(String s, MovieRecyclerViewAdapter adapter){
        this.adapter = adapter;;
        new AsyncCaller().execute(s);
    }

    private class AsyncCaller extends AsyncTask<String, Void, String>{

        private static final String API_URL = "https://api.themoviedb.org/3/movie/";
        private static final String POPULAR_PATH = "popular";
        private static final String TOP_RATED_PATH = "top_rated";
        // TODO: insert API key here
        private static final String API_KEY = "";
        private static final String API_KEY_KEY = "api_key";
        private static final String IMAGE_URL = "https://image.tmdb.org/t/p/w185";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ArrayList<Movie>movies = new ArrayList<>();
            try{
                JSONObject mainResponse = new JSONObject(s);
                JSONArray results = mainResponse.getJSONArray("results");
                for(int i=0; i<results.length(); ++i){
                    JSONObject result = results.getJSONObject(i);
                    String title = result.getString("title");
                    String overview = result.getString("overview");
                    int id = result.getInt("id");
                    double vote = result.getDouble("vote_average");
                    String url_path = IMAGE_URL + result.getString("poster_path");

                    String date_str = result.getString("release_date");
                    DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
                    try {
                        Date date = format.parse(date_str);
                        DateFormat l_format = new SimpleDateFormat("dd MMM, yyyy", Locale.getDefault());
                        date_str = l_format.format(date);
                    }catch(ParseException e){

                    }
                    Movie m = new Movie(title, overview, id, vote, url_path, date_str);
                    new ImageLoaderTask().execute(m);
                    movies.add(m);
                }
            } catch(JSONException e){
                e.printStackTrace();
            }
            adapter.setmData(movies);
        }

        @Override
        protected String doInBackground(String... strings) {
            String query = strings[0];
            Uri uri;
            if(query.equals("mostPopular")){
                uri = Uri.parse(API_URL)
                        .buildUpon()
                        .appendPath(POPULAR_PATH)
                        .appendQueryParameter(API_KEY_KEY, API_KEY)
                        .build();
            } else if(query.equals("highestRated")){
                uri = Uri.parse(API_URL)
                        .buildUpon()
                        .appendPath(TOP_RATED_PATH)
                        .appendQueryParameter(API_KEY_KEY, API_KEY)
                        .build();
            } else {
                return null;
            }
            URL url;
            try {
                url = new URL(uri.toString());
            } catch(MalformedURLException e){
                e.printStackTrace();
                return null;
            }
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.getRequestMethod();
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                Scanner scanner = new Scanner(inputStream);
                scanner.useDelimiter("\\A");
                boolean hasInput = scanner.hasNext();
                if(hasInput)
                    return scanner.next();
                else
                    return null;
            } catch(IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public class ImageLoaderTask extends AsyncTask<Movie, Void, Bitmap>{

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            adapter.imageLoad();
        }

        @Override
        protected Bitmap doInBackground(Movie... movies) {
            String urlPath = movies[0].getPosterUrl();
            URL url;
            try{
                url = new URL(urlPath);
            }catch (MalformedURLException e){
                e.printStackTrace();
                url = null;
            }
            Bitmap poster;
            try {
                poster = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            }catch (IOException e){
                e.printStackTrace();
                poster = null;
            }
            movies[0].setPoster(poster);
            return poster;
        }
    }
}
