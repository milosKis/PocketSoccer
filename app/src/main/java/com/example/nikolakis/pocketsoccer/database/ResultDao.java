package com.example.nikolakis.pocketsoccer.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ResultDao {

    @Insert
    void insert(Result result);

    @Query("DELETE FROM result_table")
    void deleteAll();

    @Query("DELETE FROM result_table where (name1=:name1 and name2=:name2) OR (name1=:name2 and name2=:name1)")
    void deleteAllResultsForPlayers(String name1, String name2);

    @Query("SELECT * from result_table order by id desc")
    LiveData<List<Result>> getAllResults();

    @Query("SELECT * FROM result_table where (name1=:name1 and name2=:name2) OR (name1=:name2 and name2=:name1) ORDER BY id desc")
    LiveData<List<Result>> getAllResultsForPlayers(String name1, String name2);

//    @Query("SELECT * FROM result_table WHERE 1=1 GROUP BY name1, name2")
//    LiveData<List<Result>> getAllPairsOfPlayers();

    @Query("SELECT id as id, name1 as name1, name2 as name2,(select count(*) from result_table S where (S.name1=B.name1 and S.name2=B.name2 and S.goals1>S.goals2)" +
            " or (S.name1=B.name2 and S.name2=B.name1 and S.goals2>S.goals1)) as goals1, (select count(*) from result_table S where (S.name1=B.name2 and S.name2=B.name1 and S.goals1>S.goals2)" +
            " or (S.name1=B.name2 and S.name2=B.name1 and S.goals2>S.goals1)) as goals2, time as time from result_table B where 1=1 group by B.name1, B.name2")
//    @Query("SELECT id as id, name1 as name1, name2 as name2, 5 as goals1, 4 goals2, time as time from result_table B where 1=1 group by B.name1, B.name2")
    LiveData<List<Result>> getAllPairsOfPlayers();
}
