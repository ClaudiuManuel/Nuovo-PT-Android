package com.example.nuovo_pt.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.nuovo_pt.R;
import com.example.nuovo_pt.api.ExerciseRepository;
import com.example.nuovo_pt.api.OnGetAPIResponseCallBack;
import com.example.nuovo_pt.api.Result;
import com.example.nuovo_pt.db.ExerciseViewModel;
import com.example.nuovo_pt.db.exercises.Exercise;
import com.example.nuovo_pt.db.workouts.Workout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class WorkoutExercisesFragment extends Fragment {
    Workout workout;
    LinearLayout exercisesLayout;
    ExerciseViewModel exerciseViewModel;
    boolean firstTimePopulated = true;
    TextView exerciseTitle;
    TextView exerciseMuscle;

    ExerciseRepository exerciseRepository;
    List<Exercise> exercises = new ArrayList<Exercise>();

    public WorkoutExercisesFragment(Workout workout) {
        this.workout = workout;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workouts_exercises, container, false);
        exercisesLayout = view.findViewById(R.id.exercises_layout);

        FloatingActionButton fab = view.findViewById(R.id.fab_add_exercise);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 getExercisesFromAPI();
            }
        });

        exerciseRepository = ExerciseRepository.getInstance();

        exerciseViewModel = new ViewModelProvider(this).get(ExerciseViewModel.class);
        exerciseViewModel.setWorkoutID(workout.getId());
        exerciseViewModel.getAllWorkoutExercises().observe(getViewLifecycleOwner(), new Observer<List<Exercise>>() {
            @Override
            public void onChanged(@Nullable final List<Exercise> exercises) {
                if(firstTimePopulated) {
                    populateExercises(inflater,container,exercises);
                    firstTimePopulated = false;
                } else {
                    View exerciseItem = (View) inflater.inflate(R.layout.exercise_item, container ,false);
                    initialiseExerciseItem(exerciseItem,exercises.get(exercises.size()-1));
                }
            }
        });
        return view;
    }

    public void getExercisesFromAPI() {
        exerciseRepository.getExercises(new OnGetAPIResponseCallBack() {
            @Override
            public void onSuccess(List<Result> exerciseListFromAPI) {
                exercises = mapResultToExercise(exerciseListFromAPI);
                getFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, new ChooseExerciseFragment(exercises,workout))
                        .commit();
            }

            @Override
            public void onError() {
                Log.d("exerciseERROR","error in getting exercises from API");
                Toast.makeText(getContext(), "API error",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private List<Exercise> mapResultToExercise(List<Result> exerciseListFromAPI) {
        List<Exercise> exerciseList = new ArrayList<>();
        for (Result result : exerciseListFromAPI) {
            exerciseList.add(new Exercise(result.getName(),result.getCategory().getName()));
        }
        return exerciseList;
    }

    void populateExercises(LayoutInflater inflater, ViewGroup container, List<Exercise> exercises) {
        for(Exercise exercise:exercises) {
            View exerciseItem = (View) inflater.inflate(R.layout.exercise_item, container ,false);
            initialiseExerciseItem(exerciseItem,exercise);
        }
    }

    void initialiseExerciseItem(View exerciseItem, Exercise exercise) {
        exerciseTitle = exerciseItem.findViewById(R.id.exercise_title);
        exerciseMuscle = exerciseItem.findViewById(R.id.exercise_muscle);
        exerciseTitle.setText(exercise.getExerciseName());
        exerciseMuscle.setText(exercise.getTargetedMuscle());
        exercisesLayout.addView(exerciseItem);
    }

}
