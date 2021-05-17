package com.example.nuovo_pt.db.exercises;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "exercises")
public class Exercise {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "name")
    private String exerciseName;

    @NonNull
    @ColumnInfo(name = "muscle")
    private String targetedMuscle;

    @ColumnInfo(name = "workoutID")
    private int workoutID;

    public Exercise(String exerciseName, String targetedMuscle, int workoutID) {
        this.exerciseName = exerciseName;
        this.targetedMuscle = targetedMuscle;
        this.workoutID = workoutID;
    }

    @Ignore
    public Exercise(String exerciseName, String targetedMuscle) {
        this.exerciseName = exerciseName;
        this.targetedMuscle = targetedMuscle;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public String getTargetedMuscle() {
        return targetedMuscle;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public void setTargetedMuscle(String targetedMuscle) {
        this.targetedMuscle = targetedMuscle;
    }

    public int getWorkoutID() {
        return workoutID;
    }

    public void setWorkoutID(int workoutID) {
        this.workoutID = workoutID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
