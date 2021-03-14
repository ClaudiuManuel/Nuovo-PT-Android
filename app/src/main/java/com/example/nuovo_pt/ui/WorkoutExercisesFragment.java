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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.nuovo_pt.R;
import com.example.nuovo_pt.api.ExerciseRepository;
import com.example.nuovo_pt.api.OnGetAPIResponseCallBack;
import com.example.nuovo_pt.api.Result;
import com.example.nuovo_pt.db.ExerciseViewModel;
import com.example.nuovo_pt.db.exercises.Exercise;
import com.example.nuovo_pt.db.exercises.ExerciseFirebase;
import com.example.nuovo_pt.db.workouts.Workout;
import com.example.nuovo_pt.db.workouts.WorkoutFirebase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WorkoutExercisesFragment extends Fragment implements View.OnClickListener{
    String workoutID;
    LinearLayout exercisesLayout;
    ExerciseViewModel exerciseViewModel;
    boolean firstTimePopulated = true;
    TextView exerciseTitle;
    TextView exerciseMuscle;
    FloatingActionButton fab;
    LayoutInflater inflater;
    ViewGroup container;
    NavController navController;
    DatabaseReference databaseReference;
    List<ExerciseFirebase> exercises = new ArrayList<>();

    public WorkoutExercisesFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        workoutID = getArguments().getString("workoutID");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab = view.findViewById(R.id.fab_add_exercise);
        fab.setOnClickListener(this);
        navController = Navigation.findNavController(view);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workouts_exercises, container, false);
        exercisesLayout = view.findViewById(R.id.exercises_layout);
        databaseReference = FirebaseDatabase.getInstance().getReference("Exercises");
        this.inflater = inflater;
        this.container = container;

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                exercises.clear();
                for(DataSnapshot exerciseSnapShot : snapshot.getChildren()) {
                    ExerciseFirebase exerciseFirebase = exerciseSnapShot.getValue(ExerciseFirebase.class);
                    if(exerciseFirebase.getWorkoutID().equals(workoutID) )
                        exercises.add(exerciseFirebase);
                }

                populateExercises(exercises);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void populateExercises(List<ExerciseFirebase> exercises) {
        for(ExerciseFirebase exercise:exercises) {
            View exerciseItem = (View) inflater.inflate(R.layout.exercise_item, container ,false);
            initialiseExerciseItem(exerciseItem,exercise);
        }
    }

    void initialiseExerciseItem(View exerciseItem, ExerciseFirebase exercise) {
        exerciseTitle = exerciseItem.findViewById(R.id.exercise_title);
        exerciseMuscle = exerciseItem.findViewById(R.id.exercise_muscle);
        exerciseTitle.setText(exercise.getExerciseName());
        exerciseMuscle.setText(exercise.getMuscleTargeted());
        exercisesLayout.addView(exerciseItem);
    }

    @Override
    public void onClick(View v) {
        if (v != null && v == fab) {
            Bundle bundle = new Bundle();
            bundle.putString("workoutID", workoutID);
            navController.navigate(R.id.action_workoutExercisesFragment2_to_chooseExerciseFragment, bundle);
        }
    }
}
