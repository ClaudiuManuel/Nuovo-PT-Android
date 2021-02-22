package com.example.nuovo_pt.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;

import com.example.nuovo_pt.db.clients.Client;
import com.example.nuovo_pt.db.clients.ClientDao;
import com.example.nuovo_pt.db.exercises.Exercise;
import com.example.nuovo_pt.db.exercises.ExerciseDao;
import com.example.nuovo_pt.db.workouts.Workout;
import com.example.nuovo_pt.db.workouts.WorkoutDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Client.class, Workout.class, Exercise.class}, version = 2, exportSchema = false)
public abstract class NuovoRoomDatabase extends androidx.room.RoomDatabase {

    public abstract ClientDao clientDao();
    public abstract WorkoutDao workoutDao();
    public abstract ExerciseDao exerciseDao();

    private static volatile NuovoRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 5;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static NuovoRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NuovoRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NuovoRoomDatabase.class, "nuovo_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
