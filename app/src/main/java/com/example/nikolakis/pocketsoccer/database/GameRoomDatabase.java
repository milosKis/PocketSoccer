package com.example.nikolakis.pocketsoccer.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Result.class, SavedGame.class}, version = 1)
public abstract class GameRoomDatabase extends RoomDatabase {

    public abstract ResultDao resultDao();
    public abstract SavedGameDao savedGameDao();

    private static volatile GameRoomDatabase INSTANCE;

    static GameRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GameRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            GameRoomDatabase.class, "game_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
