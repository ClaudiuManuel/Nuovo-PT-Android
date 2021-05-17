package com.example.nuovo_pt.db.exercises;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExerciseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Exercise Exercise);

//    @Query("DELETE FROM exercises")
//    void deleteAll();

    @Query("SELECT * from exercises WHERE workoutID = :workoutID")
    LiveData<List<Exercise>> getWorkoutExercises(int workoutID);
}
