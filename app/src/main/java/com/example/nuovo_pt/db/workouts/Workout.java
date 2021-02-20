package com.example.nuovo_pt.db.workouts;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.nuovo_pt.db.clients.Client;

@Entity(foreignKeys = @ForeignKey(entity = Client.class,
        parentColumns = "name",
        childColumns = "client_name",
        onDelete = ForeignKey.CASCADE),
        tableName = "workouts")
public class Workout {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "name")
    private String workoutName;

    @NonNull
    @ColumnInfo(name = "targeted_muscle")
    private String muscleGroup;

    @NonNull
    @ColumnInfo(name = "client_name")
    private String clientName;

    public Workout(String workoutName,String muscleGroup,String clientName) {
        this.workoutName = workoutName;
        this.muscleGroup = muscleGroup;
        this.clientName = clientName;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWorkoutName(@NonNull String workoutName) {
        this.workoutName = workoutName;
    }

    public void setMuscleGroup(@NonNull String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public void setClientName(@NonNull String clientName) {
        this.clientName = clientName;
    }

    @NonNull
    public String getClientName() {
        return clientName;
    }
}
