package utils;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MovieDAO{
    @Insert(onConflict = REPLACE)
    void addFavourite(Movie movie);

    @Delete
    void deleteFavourite(Movie movie);

    @Query("SELECT * FROM movie WHERE id = :id")
    Movie isFavourite(int id);

    @Query("SELECT * FROM movie WHERE id = :id")
    LiveData<Movie> isFavouriteLive(int id);

    @Query("SELECT * FROM movie")
    LiveData<List<Movie>> allFavourite();

    @Query("SELECT * FROM movie")
    List<Movie> favourites();
}