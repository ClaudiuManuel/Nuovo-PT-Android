package com.example.nuovo_pt.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.nuovo_pt.db.exercises.Exercise;
import com.example.nuovo_pt.db.workouts.Workout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ClientWorkoutsFragment extends Fragment implements View.OnClickListener {
    String clientName;
    LinearLayout workoutsLayout;
    CardView workoutCardView;
    TextView workoutTitle;
    TextView workoutMuscle;
    List<Workout> workoutList;
    NavController navController = null;
    WorkoutViewModel workoutViewModel;
    boolean firstTimePopulated = true;
    FloatingActionButton fab;

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

        workoutViewModel = new ViewModelProvider(this).get(WorkoutViewModel.class);
        workoutViewModel.setClientName(clientName);
        workoutViewModel.getAllClientWorkouts().observe(getViewLifecycleOwner(), new Observer<List<Workout>>() {
            @Override
            public void onChanged(@Nullable final List<Workout> workouts) {
                if(firstTimePopulated) {
                    workoutList = workouts;
                    populateWorkoutsFragment(inflater,container,workouts);
                }
//                else {
//                    View cardviewWorkout = (View) inflater.inflate(R.layout.cardview_workout, container ,false);
//                    initialiseWorkoutCardview(cardviewWorkout,workouts.get(workouts.size()-1));
//                }
            }
        });

        return view;
    }

    void populateWorkoutsFragment(LayoutInflater inflater, ViewGroup container, List<Workout> workouts) {
        for(Workout workout:workouts) {
            View cardviewWorkout = (View) inflater.inflate(R.layout.cardview_workout, container ,false);
            initialiseWorkoutCardview(cardviewWorkout,workout);
        }
    }

    void initialiseWorkoutCardview(View cardviewWorkout, Workout workout) {
        workoutTitle = cardviewWorkout.findViewById(R.id.workoutTitle);
        workoutMuscle = cardviewWorkout.findViewById(R.id.workoutMuscles);
        workoutTitle.setText(workout.getWorkoutName());
        workoutMuscle.setText(workout.getMuscleGroup());
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
            int workoutID = searchWorkouts(workoutList,workoutTitle);
            Bundle bundle = new Bundle();
            bundle.putInt("workoutID",workoutID);
            navController.navigate(R.id.action_clientWorkoutsFragment_to_workoutExercisesFragment2,bundle);
        }
    }

    public int searchWorkouts(List<Workout> workouts, String workoutTitle) {
        for(Workout workout:workouts) {
            if(workout.getWorkoutName().equals(workoutTitle))
                return workout.getId();
        }
        return -1;
    }
}