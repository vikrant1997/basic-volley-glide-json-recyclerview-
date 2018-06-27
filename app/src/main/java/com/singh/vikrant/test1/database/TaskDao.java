package com.singh.vikrant.test1.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.singh.vikrant.test1.AnimeAdapter;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task")
    List<Anime_Model> loadAllTasks();


    @Query("SELECT ID FROM task where TITLE=:animeTitle")
    String loadStarValue(String animeTitle);

    @Insert
    void insertTask(Anime_Model taskEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(Anime_Model taskEntry);

    @Delete
    void deleteTask(Anime_Model taskEntry);
}
