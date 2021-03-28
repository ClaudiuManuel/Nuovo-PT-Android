package com.example.nuovo_pt.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.nuovo_pt.api.ExerciseRepository;
import com.example.nuovo_pt.api.OnGetAPIResponseCallBack;
import com.example.nuovo_pt.api.Result;
import com.example.nuovo_pt.db.WorkoutViewModel;
import com.example.nuovo_pt.db.clients.Client;
import com.example.nuovo_pt.R;
import com.example.nuovo_pt.db.clients.ClientFirebase;
import com.example.nuovo_pt.db.exercises.Exercise;
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

public class ClientWorkoutsFragment extends Fragment implements View.OnClickListener {
    String clientName;
    LinearLayout workoutsLayout;
    TextView workoutTitle;
    LayoutInflater inflater;
    ViewGroup container;
    TextView workoutLength,workoutLevel,workoutDate;
    String workoutMuscle;
    ImageView workoutIcon;
    NavController navController = null;
    List<WorkoutFirebase> workouts = new ArrayList<>();
    FloatingActionButton fab;
    private DatabaseReference databaseReference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clientName = getArguments().getString("clientName");
    }

    public ClientWorkoutsFragment() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        workoutsLayout = view.findViewById(R.id.workouts_layout);
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_client_workouts, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference("Workouts");

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
                workouts.clear();
                for(DataSnapshot workoutSnapshot : snapshot.getChildren()) {
                    WorkoutFirebase workoutFirebase = workoutSnapshot.getValue(WorkoutFirebase.class);
                    if(workoutFirebase.getClientsName().equals(clientName) )
                        workouts.add(workoutFirebase);
                }

                populateWorkoutsFragment(workouts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void populateWorkoutsFragment(List<WorkoutFirebase> workouts) {
        for(WorkoutFirebase workout:workouts) {
            View cardviewWorkout = (View) inflater.inflate(R.layout.cardview_workout, container ,false);
            initialiseWorkoutCardview(cardviewWorkout,workout);
        }
    }

    void initialiseWorkoutCardview(View cardviewWorkout, WorkoutFirebase workout) {
        workoutTitle = cardviewWorkout.findViewById(R.id.workoutTitle);
        workoutLevel = cardviewWorkout.findViewById(R.id.workoutLevelTextView);
        workoutLength = cardviewWorkout.findViewById(R.id.workoutLengthTextView);
        workoutDate = cardviewWorkout.findViewById(R.id.workoutDateTextview);
        workoutIcon = cardviewWorkout.findViewById(R.id.workoutIcon);
        workoutTitle.setText(workout.getWorkoutName());
        workoutLevel.setText(workout.getWorkoutLevel());
        workoutLength.setText(workout.getWorkoutLength());
        workoutDate.setText(workout.getWorkoutDate());
        workoutsLayout.addView(cardviewWorkout);
        cardviewWorkout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v != null && v==fab) {
            Bundle bundle = new Bundle();
            bundle.putString("clientName",clientName);
            navController.navigate(R.id.action_clientWorkoutsFragment_to_newWorkoutFragment2,bundle);
        } else {
            TextView workoutTitleTextView = v.findViewById(R.id.workoutCardView).findViewById(R.id.workoutTitle);
            String workoutTitle = workoutTitleTextView.getText().toString();
            String workoutID = searchWorkouts(workouts,workoutTitle);
            Bundle bundle = new Bundle();
            bundle.putString("workoutID",workoutID);
            navController.navigate(R.id.action_clientWorkoutsFragment_to_workoutExercisesFragment2,bundle);
        }
    }

    public String searchWorkouts(List<WorkoutFirebase> workouts, String workoutTitle) {
        for(WorkoutFirebase workout:workouts) {
            if(workout.getWorkoutName().equals(workoutTitle))
                return workout.getWorkoutID();
        }
        return null;
    }
}