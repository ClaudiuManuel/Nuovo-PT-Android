package com.example.nuovo_pt.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.nuovo_pt.db.exercises.Exercise;
import com.example.nuovo_pt.db.exercises.ExerciseDao;

import java.util.List;

public class ExerciseRepository {
    private ExerciseDao exerciseDao;
    private LiveData<List<Exercise>> allWorkoutExercises;

    ExerciseRepository(Application application) {
        NuovoRoomDatabase db = NuovoRoomDatabase.getDatabase(application);
        exerciseDao = db.exerciseDao();

    }

    LiveData<List<Exercise>> getAllWorkoutExercises(int workoutID) {
        allWorkoutExercises = exerciseDao.getWorkoutExercises(workoutID);
        return allWorkoutExercises;
    }

    void insert(Exercise exercise) {
        NuovoRoomDatabase.databaseWriteExecutor.execute(() -> {
            System.out.println("exercise repository insert  " + exercise.getWorkoutID() + "  " + exercise.getExerciseName());
            exerciseDao.insert(exercise);
        });
    }
}
