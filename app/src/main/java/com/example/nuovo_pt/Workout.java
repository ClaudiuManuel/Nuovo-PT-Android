package com.example.nuovo_pt;

public class Workout {

    private String workoutName;
    private String muscleGroup;

    public Workout(String workoutName,String muscleGroup) {
        this.workoutName = workoutName;
        this.muscleGroup = muscleGroup;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }
}
