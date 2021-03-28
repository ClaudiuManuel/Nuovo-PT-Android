package com.example.nuovo_pt.db.workouts;

public class WorkoutFirebase {
    String muscleTargeted;
    String clientsName;
    String workoutName;
    String workoutID;
    String workoutDate;
    String workoutLength;
    String workoutLevel;

    public WorkoutFirebase(){}

    public WorkoutFirebase(String workoutID, String workoutName, String workoutLength, String workoutLevel, String workoutDate, String muscleTargeted, String clientsName) {
        this.muscleTargeted = muscleTargeted;
        this.clientsName = clientsName;
        this.workoutName = workoutName;
        this.workoutDate = workoutDate;
        this.workoutLength = workoutLength;
        this.workoutLevel = workoutLevel;
        this.workoutID = workoutID;
    }

    public String getMuscleTargeted() {
        return muscleTargeted;
    }

    public void setMuscleTargeted(String muscleTargeted) {
        this.muscleTargeted = muscleTargeted;
    }

    public String getClientsName() {
        return clientsName;
    }

    public void setClientsName(String clientsName) {
        this.clientsName = clientsName;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public String getWorkoutID() {
        return workoutID;
    }

    public void setWorkoutID(String workoutID) {
        this.workoutID = workoutID;
    }

    public String getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutDate(String workoutDate) {
        this.workoutDate = workoutDate;
    }

    public String getWorkoutLength() {
        return workoutLength;
    }

    public void setWorkoutLength(String workoutLength) {
        this.workoutLength = workoutLength;
    }

    public String getWorkoutLevel() {
        return workoutLevel;
    }

    public void setWorkoutLevel(String workoutLevel) {
        this.workoutLevel = workoutLevel;
    }
}
