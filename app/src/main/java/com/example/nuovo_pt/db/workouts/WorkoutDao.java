package com.example.nuovo_pt.db.workouts;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.nuovo_pt.db.clients.Client;

import java.util.List;

@Dao
public interface WorkoutDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Workout workout);

//    @Query("DELETE FROM workouts")
//    void deleteAll();

    @Query("SELECT * from workouts WHERE client_name = :clientName")
    LiveData<List<Workout>> getClientsWorkouts(String clientName);
}
