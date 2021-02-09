package com.example.nuovo_pt;

import java.util.ArrayList;
import java.util.List;

public class Client {

    private String name;
    private boolean isMale=true;
    private List<Workout> workouts;

    public Client (String name,boolean isMale) {
        this.name = name;
        this.isMale = isMale;
        workouts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public boolean getSex () {
        return isMale;
    }

    public void addWorkout (Workout workout) {
        workouts.add(workout);
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }
}
