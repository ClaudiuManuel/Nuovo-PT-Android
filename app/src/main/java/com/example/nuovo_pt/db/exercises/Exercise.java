package com.example.nuovo_pt.db.exercises;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.nuovo_pt.db.workouts.Workout;

@Entity(foreignKeys = @ForeignKey(entity = Workout.class,
    parentColumns = "id",
    childColumns = "workoutID",
    onDelete = ForeignKey.CASCADE),
    tableName = "exercises")
public class Exercise {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private String exerciseName;

    @NonNull
    @ColumnInfo(name = "muscle")
    private String targetedMuscle;

    @NonNull
    @ColumnInfo(name = "workoutID")
    private int workoutID;

    public Exercise(String exerciseName, String targetedMuscle, int workoutID) {
        this.exerciseName = exerciseName;
        this.targetedMuscle = targetedMuscle;
        this.workoutID = workoutID;
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
}
