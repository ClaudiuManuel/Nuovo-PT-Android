package com.example.nuovo_pt.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChooseExerciseFragment extends Fragment {
    String workoutID;
    static String workoutMuscle;
    ExerciseViewModel exerciseViewModel;
    ExerciseRepository exerciseRepository;
    List<Exercise> exercises = new ArrayList<>();
    private ProgressBar progressBar;
    ExerciseAdapter adapter;
    DatabaseReference databaseReference;
    static List<Result> allExercises = new ArrayList<>();
    static boolean isFirstTimeFetched = true;

    public ChooseExerciseFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_exercise, container, false);
        workoutID = getArguments().getString("workoutID");

        DatabaseReference workoutReference = FirebaseDatabase.getInstance().getReference("Workouts").child(workoutID);
        workoutReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                WorkoutFirebase workoutFirebase = snapshot.getValue(WorkoutFirebase.class);
                if(workoutFirebase != null) {
                    workoutMuscle = workoutFirebase.getMuscleTargeted();
                    getExercisesFromAPI();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.exercises_recyclerview);
        exerciseViewModel = new ViewModelProvider(this).get(ExerciseViewModel.class);
        adapter = new ExerciseAdapter(this.getContext(),exercises,new OnItemClickListener() {
            @Override
            public void onItemClick(Exercise exercise) {
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference("Exercises");
        progressBar = view.findViewById(R.id.exercises_progressBar);
    }

    @Override
    public void onStart() {
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
    }

    public void getExercisesFromAPI() {
        exerciseRepository = ExerciseRepository.getInstance();
        if(isFirstTimeFetched){
            exerciseRepository.getExercises(new OnGetAPIResponseCallBack() {
                @Override
                public void onSuccess(List<Result> exerciseListFromAPI) {
                    allExercises = exerciseListFromAPI;
                    exercises = mapResultToExercise(exerciseListFromAPI);
                    progressBar.setVisibility(View.GONE);
                    adapter.setExercises(exercises);
                }

                @Override
                public void onError() {
                    Log.d("exerciseERROR","error in getting exercises from API");
                    Toast.makeText(getContext(), "API error",
                            Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            });
            isFirstTimeFetched = false;
        } else {
            progressBar.setVisibility(View.GONE);
            adapter.setExercises(mapResultToExercise(allExercises));
        }
    }

    private List<Exercise> mapResultToExercise(List<Result> exerciseListFromAPI) {
        List<Exercise> exerciseList = new ArrayList<>();
        for (Result result : exerciseListFromAPI) {
            if(result.getCategory().getName().equals(workoutMuscle))
                exerciseList.add(new Exercise(result.getName(),result.getCategory().getName()));
        }
        return exerciseList;
    }


}
