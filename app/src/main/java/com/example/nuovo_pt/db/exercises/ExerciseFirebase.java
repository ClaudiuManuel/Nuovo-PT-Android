package com.example.nuovo_pt.db.exercises;

public class ExerciseFirebase {

    String muscleTargeted;
    String exerciseName;
    String workoutID;
    String exerciseID;

    public ExerciseFirebase(){}

    public ExerciseFirebase(String workoutID, String exerciseID, String muscleTargeted, String exerciseName) {
        this.muscleTargeted = muscleTargeted;
        this.exerciseName = exerciseName;
        this.workoutID = workoutID;
        this.exerciseID = exerciseID;
    }

    public String getMuscleTargeted() {
        return muscleTargeted;
    }

    public void setMuscleTargeted(String muscleTargeted) {
        this.muscleTargeted = muscleTargeted;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getWorkoutID() {
        return workoutID;
    }

    public void setWorkoutID(String workoutID) {
        this.workoutID = workoutID;
    }

    public String getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(String exerciseID) {
        this.exerciseID = exerciseID;
    }
}
