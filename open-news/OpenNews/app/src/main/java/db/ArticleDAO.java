package db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ArticleDAO{
    @Insert(onConflict = REPLACE)
    void save(ArticleDB article);

    @Delete
    void remove(ArticleDB article);

    @Query("SELECT * FROM ArticleDB")
    List<ArticleDB> get();
}