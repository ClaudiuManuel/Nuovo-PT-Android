package com.example.nuovo_pt.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.nuovo_pt.db.exercises.Exercise;
import com.example.nuovo_pt.db.workouts.Workout;

import java.util.List;

public class ExerciseViewModel extends AndroidViewModel {
    private ExerciseRepository exerciseRepository;
    private LiveData<List<Exercise>> allWorkoutExercises;
    private int workoutID;
    private Application application;
    private boolean isInitialised = false;

    public ExerciseViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public void initialise(Application application) {
        exerciseRepository = new ExerciseRepository(application, workoutID);
        allWorkoutExercises = exerciseRepository.getAllWorkoutExercises();
    }

    public LiveData<List<Exercise>> getAllWorkoutExercises() {
        if(!isInitialised)
            initialise(application);
        return allWorkoutExercises;
    }

    public void insert(Exercise exercise) {
        if(!isInitialised)
            initialise(application);
        exerciseRepository.insert(exercise);
    }

    public void setWorkoutID(Integer workoutID) {
        this.workoutID = workoutID;
    }
}