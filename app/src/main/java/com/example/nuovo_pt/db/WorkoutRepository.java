package com.example.nuovo_pt.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.nuovo_pt.db.clients.Client;
import com.example.nuovo_pt.db.workouts.Workout;
import com.example.nuovo_pt.db.workouts.WorkoutDao;

import java.util.List;

public class WorkoutRepository {
    private WorkoutDao workoutDao;
    private LiveData<List<Workout>> allClientWorkouts;

    WorkoutRepository(Application application, String clientName) {
        NuovoRoomDatabase db = NuovoRoomDatabase.getDatabase(application);
        workoutDao = db.workoutDao();
        allClientWorkouts = workoutDao.getClientsWorkouts(clientName);
    }

    LiveData<List<Workout>> getAllClientWorkouts() {
        return allClientWorkouts;
    }

    void insert(Workout workout) {
        NuovoRoomDatabase.databaseWriteExecutor.execute(() -> {
            workoutDao.insert(workout);
        });
    }
}
