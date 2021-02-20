package com.example.nuovo_pt.db.clients;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.nuovo_pt.db.workouts.Workout;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "clients")
public class Client {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "sex")
    private int isMale;

    @Ignore
    private List<Workout> workouts;

    public Client (String name,int isMale) {
        this.name = name;
        this.isMale = isMale;
        workouts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getIsMale() {
        return isMale;
    }

    public void addWorkout (Workout workout) {
        workouts.add(workout);
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }
}
