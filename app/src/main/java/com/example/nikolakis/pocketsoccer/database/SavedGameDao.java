package com.example.nikolakis.pocketsoccer.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface SavedGameDao {
    @Insert
    void insert(SavedGame savedGame);

    @Query("DELETE FROM saved_game_table")
    void deleteAll();

    @Query("SELECT * from saved_game_table limit 1")
    LiveData<List<SavedGame>> getSavedGames();
}
