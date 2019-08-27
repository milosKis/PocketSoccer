package com.example.nikolakis.pocketsoccer.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "result_table")
public class Result {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int _id;

    @NonNull
    @ColumnInfo(name = "name1")
    private String name1;

    @NonNull
    @ColumnInfo(name = "name2")
    private String name2;

    @NonNull
    @ColumnInfo(name = "goals1")
    private int goals1;

    @NonNull
    @ColumnInfo(name = "goals2")
    private int goals2;

    @NonNull
    @ColumnInfo(name = "time")
    private String time;

    public Result(@NonNull String name1, @NonNull String name2, @NonNull int goals1, @NonNull int goals2, @NonNull String time) {
        this.name1 = name1;
        this.name2 = name2;
        this.goals1 = goals1;
        this.goals2 = goals2;
        this.time = time;
    }

    @NonNull
    public int get_id() {
        return _id;
    }

    @NonNull
    public String getName1() {
        return name1;
    }

    @NonNull
    public String getName2() {
        return name2;
    }

    @NonNull
    public int getGoals1() {
        return goals1;
    }

    @NonNull
    public int getGoals2() {
        return goals2;
    }

    @NonNull
    public String getTime() {
        return time;
    }

    public void set_id(@NonNull int _id) {
        this._id = _id;
    }

    public void setGoals1(@NonNull int goals1) {
        this.goals1 = goals1;
    }

    public void setGoals2(@NonNull int goals2) {
        this.goals2 = goals2;
    }
}
