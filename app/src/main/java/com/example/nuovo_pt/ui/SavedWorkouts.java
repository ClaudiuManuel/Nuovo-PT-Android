package com.example.nuovo_pt.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nuovo_pt.R;
import com.example.nuovo_pt.db.exercises.ExerciseFirebase;
import com.example.nuovo_pt.db.workouts.WorkoutFirebase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SavedWorkouts extends Fragment implements WorkoutRecyclerViewAdapter.ItemClickListener{

    NavController navController = null;
    List<WorkoutFirebase> workouts = new ArrayList<>();
    private DatabaseReference databaseReference,databaseReferenceExercises;
    String clientName;

    WorkoutRecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    public SavedWorkouts() {

    }

    // Declare Context variable at class level in Fragment
    private Context mContext;

    // Initialise it from onAttach()
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clientName = getArguments().getString("clientName");
    }

    @Override
    public void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                workouts.clear();
                for(DataSnapshot workoutSnapshot : snapshot.getChildren()) {
                    WorkoutFirebase workoutFirebase = workoutSnapshot.getValue(WorkoutFirebase.class);
                    if(workoutFirebase.getFavorite().equals("true") )
                        workouts.add(workoutFirebase);
                }

                populateWorkoutsFragment(workouts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved_workouts, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference("Workouts");
        databaseReferenceExercises = FirebaseDatabase.getInstance().getReference("Exercises");

        recyclerView = view.findViewById(R.id.savedWorkoutsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

    }

    void populateWorkoutsFragment(List<WorkoutFirebase> workouts) {
        adapter = new WorkoutRecyclerViewAdapter(mContext, workouts);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        WorkoutFirebase workoutFirebase = adapter.getWorkout(position);

        if(clientName.equals("0")) {
            Bundle bundle = new Bundle();
            bundle.putString("workoutID",workoutFirebase.getWorkoutID());
            navController.navigate(R.id.action_savedWorkouts_to_workoutExercisesFragment2,bundle);
        } else {
            String workoutName = workoutFirebase.getWorkoutName();
            String date = workoutFirebase.getWorkoutDate();
            String level = workoutFirebase.getWorkoutLevel();
            String estimatedLength = workoutFirebase.getWorkoutLength();
            String selectedMuscleGroup = workoutFirebase.getMuscleTargeted();
            String workoutID = databaseReference.push().getKey();

            WorkoutFirebase workoutToBeAdded = new WorkoutFirebase(workoutID,workoutName,estimatedLength,level, "false",date,selectedMuscleGroup,clientName);
            databaseReference.child(workoutID).setValue(workoutToBeAdded);

            //creating a new exercise for each exercise found in the copied workout and assigning it to the new workout
            databaseReferenceExercises.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot exerciseSnapShot : dataSnapshot.getChildren()) {
                        ExerciseFirebase exerciseFirebase = exerciseSnapShot.getValue(ExerciseFirebase.class);
                        if(exerciseFirebase.getWorkoutID().equals(workoutFirebase.getWorkoutID())) {
                            String exerciseID = databaseReferenceExercises.push().getKey();
                            ExerciseFirebase exerciseToBeAdded = new ExerciseFirebase(workoutID,exerciseID,exerciseFirebase.getMuscleTargeted(),exerciseFirebase.getExerciseName());
                            databaseReferenceExercises.child(exerciseID).setValue(exerciseToBeAdded);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // ...
                }
            });

            Toast feedback = Toast.makeText(getContext(), "Workout added successfully:  " + workoutName, Toast.LENGTH_LONG);
            feedback.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 230);
            feedback.show();
        }
    }
}