package com.example.nuovo_pt.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nuovo_pt.OnItemClickListener;
import com.example.nuovo_pt.R;
import com.example.nuovo_pt.api.ExerciseAdapter;
import com.example.nuovo_pt.api.ExerciseRepository;
import com.example.nuovo_pt.api.OnGetAPIResponseCallBack;
import com.example.nuovo_pt.api.Result;
import com.example.nuovo_pt.db.ExerciseViewModel;
import com.example.nuovo_pt.db.WorkoutViewModel;
import com.example.nuovo_pt.db.exercises.Exercise;
import com.example.nuovo_pt.db.exercises.ExerciseFirebase;
import com.example.nuovo_pt.db.workouts.Workout;
import com.example.nuovo_pt.db.workouts.WorkoutFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChooseExerciseFragment extends Fragment {
    String workoutID;
    ExerciseViewModel exerciseViewModel;
    ExerciseRepository exerciseRepository;
    List<Exercise> exercises = new ArrayList<>();
    ExerciseAdapter adapter;
    DatabaseReference databaseReference;

    public ChooseExerciseFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExercisesFromAPI();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference("Exercises");
    }

    public void getExercisesFromAPI() {
        exerciseRepository = ExerciseRepository.getInstance();
        exerciseRepository.getExercises(new OnGetAPIResponseCallBack() {
            @Override
            public void onSuccess(List<Result> exerciseListFromAPI) {
                exercises = mapResultToExercise(exerciseListFromAPI);
                adapter.setExercises(exercises);
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_exercise, container, false);
        workoutID = getArguments().getString("workoutID");
        RecyclerView recyclerView = view.findViewById(R.id.exercises_recyclerview);
        exerciseViewModel = new ViewModelProvider(this).get(ExerciseViewModel.class);
        adapter = new ExerciseAdapter(this.getContext(),exercises,new OnItemClickListener() {
            @Override
            public void onItemClick(Exercise exercise) {
//                Exercise exerciseToBeAdded = new Exercise(exercise.getExerciseName(),exercise.getTargetedMuscle(),workoutID);
//                exerciseViewModel.insert(exerciseToBeAdded);
                String exerciseID = databaseReference.push().getKey();
                ExerciseFirebase exerciseFirebase = new ExerciseFirebase(workoutID,exerciseID,exercise.getTargetedMuscle(),exercise.getExerciseName());
                databaseReference.child(exerciseID).setValue(exerciseFirebase);
                Toast.makeText(getContext(), "Exercise added successfully", Toast.LENGTH_LONG).show();
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return view;
    }
}
