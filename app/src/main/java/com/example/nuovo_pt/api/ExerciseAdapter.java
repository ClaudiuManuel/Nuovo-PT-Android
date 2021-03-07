package com.example.nuovo_pt.api;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nuovo_pt.OnItemClickListener;
import com.example.nuovo_pt.R;
import com.example.nuovo_pt.db.exercises.Exercise;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>{

    class ExerciseViewHolder extends RecyclerView.ViewHolder {
        private final TextView exerciseNameTextView;
        private final TextView exerciseMuscleTextView;

        public ExerciseViewHolder(View itemView) {
            super(itemView);
            exerciseNameTextView = itemView.findViewById(R.id.exercise_title);
            exerciseMuscleTextView = itemView.findViewById(R.id.exercise_muscle);
        }

        public void bind(Exercise exercise, OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(exercise);
                }
            });
        }
    }

    private List<Exercise> exercises;
    private Context context;
    private final OnItemClickListener listener;

    public ExerciseAdapter(Context context, List<Exercise> exerciseList, OnItemClickListener listener) {
        this.context = context;
        this.exercises = exerciseList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.exercise_item, parent,false);
        return new ExerciseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        holder.bind(exercises.get(position), listener);
        Exercise exercise = exercises.get(position);
        holder.exerciseNameTextView.setText(exercise.getExerciseName());
        holder.exerciseMuscleTextView.setText(exercise.getTargetedMuscle());
    }

    @Override
    public int getItemCount() {
        if (exercises != null)
            return exercises.size();
        else return 0;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
        notifyDataSetChanged();
    }
}
