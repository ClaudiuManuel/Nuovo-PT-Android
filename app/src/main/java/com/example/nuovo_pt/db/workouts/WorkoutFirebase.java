package com.example.nuovo_pt.db.workouts;

public class WorkoutFirebase {
    String muscleTargeted;
    String clientsName;
    String workoutName;
    String workoutID;
    String workoutDate;
    String workoutSets;
    String workoutReps;

    public WorkoutFirebase(){}

    public WorkoutFirebase(String workoutID, String workoutName,String workoutSets,String workoutReps,String workoutDate, String muscleTargeted, String clientsName) {
        this.muscleTargeted = muscleTargeted;
        this.clientsName = clientsName;
        this.workoutName = workoutName;
        this.workoutDate = workoutDate;
        this.workoutSets = workoutSets;
        this.workoutReps = workoutReps;
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

    public String getWorkoutSets() {
        return workoutSets;
    }

    public void setWorkoutSets(String workoutSets) {
        this.workoutSets = workoutSets;
    }

    public String getWorkoutReps() {
        return workoutReps;
    }

    public void setWorkoutReps(String workoutReps) {
        this.workoutReps = workoutReps;
    }
}
