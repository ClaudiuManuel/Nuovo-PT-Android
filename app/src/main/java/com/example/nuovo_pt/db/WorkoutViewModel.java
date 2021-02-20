package com.example.nuovo_pt.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.nuovo_pt.db.workouts.Workout;

import java.util.List;

public class WorkoutViewModel extends AndroidViewModel {
    private WorkoutRepository workoutRepository;
    private LiveData<List<Workout>> allClientWorkouts;
    private String clientName;
    private Application application;
    private boolean isInitialised = false;

    public WorkoutViewModel(@NonNull Application application) {
        super(application);

    }

    public void initialise(Application application) {
        workoutRepository = new WorkoutRepository(application, clientName);
        allClientWorkouts = workoutRepository.getAllClientWorkouts();
    }

    public LiveData<List<Workout>> getAllClientWorkouts() {
        if(!isInitialised)
            initialise(application);
        return allClientWorkouts;
    }

    public void insert(Workout workout) {
        if(!isInitialised)
            initialise(application);
        workoutRepository.insert(workout);
    }

    public void setAllClientName(String name) {
        this.clientName = name;
    }
}
