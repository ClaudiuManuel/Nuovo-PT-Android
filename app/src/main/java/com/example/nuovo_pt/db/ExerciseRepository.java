package com.example.nuovo_pt.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.nuovo_pt.db.exercises.Exercise;
import com.example.nuovo_pt.db.exercises.ExerciseDao;

import java.util.List;

public class ExerciseRepository {
    private ExerciseDao exerciseDao;
    private LiveData<List<Exercise>> allWorkoutExercises;

    ExerciseRepository(Application application, int workoutID) {
        NuovoRoomDatabase db = NuovoRoomDatabase.getDatabase(application);
        exerciseDao = db.exerciseDao();
        allWorkoutExercises = exerciseDao.getWorkoutExercises(workoutID);
    }

    LiveData<List<Exercise>> getAllWorkoutExercises() {
        return allWorkoutExercises;
    }

    void insert(Exercise exercise) {
        NuovoRoomDatabase.databaseWriteExecutor.execute(() -> {
            exerciseDao.insert(exercise);
        });
    }
}
