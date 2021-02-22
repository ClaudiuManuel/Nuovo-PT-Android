package com.example.nuovo_pt.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nuovo_pt.OnItemClickListener;
import com.example.nuovo_pt.R;
import com.example.nuovo_pt.api.ExerciseAdapter;
import com.example.nuovo_pt.db.ExerciseViewModel;
import com.example.nuovo_pt.db.WorkoutViewModel;
import com.example.nuovo_pt.db.exercises.Exercise;
import com.example.nuovo_pt.db.workouts.Workout;

import java.util.List;

public class ChooseExerciseFragment extends Fragment {
    private List<Exercise> exerciseList;
    ExerciseViewModel exerciseViewModel;
    Workout workout;

    public ChooseExerciseFragment(List<Exercise> exerciseList, Workout workout) {
        this.exerciseList = exerciseList;
        this.workout = workout;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_exercise, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.exercises_recyclerview);
        exerciseViewModel = new ViewModelProvider(this).get(ExerciseViewModel.class);
        final ExerciseAdapter adapter = new ExerciseAdapter(this.getContext(),exerciseList,new OnItemClickListener() {
            @Override
            public void onItemClick(Exercise exercise) {
                Exercise exerciseToBeAdded = new Exercise(exercise.getExerciseName(),exercise.getTargetedMuscle(),workout.getId());
                exerciseViewModel.setWorkoutID(workout.getId());
                exerciseViewModel.insert(exerciseToBeAdded);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return view;
    }
}
