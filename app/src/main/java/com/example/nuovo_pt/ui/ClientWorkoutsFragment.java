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

import com.example.nuovo_pt.MainActivity;
import com.example.nuovo_pt.api.ExerciseRepository;
import com.example.nuovo_pt.api.OnGetAPIResponseCallBack;
import com.example.nuovo_pt.api.Result;
import com.example.nuovo_pt.db.ClientViewModel;
import com.example.nuovo_pt.db.WorkoutViewModel;
import com.example.nuovo_pt.db.clients.Client;
import com.example.nuovo_pt.R;
import com.example.nuovo_pt.db.exercises.Exercise;
import com.example.nuovo_pt.db.workouts.Workout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ClientWorkoutsFragment extends Fragment {
    Client client;
    LinearLayout workoutsLayout;
    CardView workoutCardView;
    TextView workoutTitle;
    TextView workoutMuscle;
    WorkoutViewModel workoutViewModel;
    boolean firstTimePopulated = true;

    ExerciseRepository exerciseRepository;
    List<Exercise> exercises = new ArrayList<Exercise>();

    public ClientWorkoutsFragment(Client client) {
        this.client = client;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_client_workouts, container, false);
        workoutsLayout = view.findViewById(R.id.workouts_layout);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                getFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, new NewWorkoutFragment(client.getName()))
                        .commit();
//                  getExercisesFromAPI();
            }
        });

        workoutViewModel = new ViewModelProvider(this).get(WorkoutViewModel.class);
        workoutViewModel.setAllClientName(client.getName());
        workoutViewModel.getAllClientWorkouts().observe(getViewLifecycleOwner(), new Observer<List<Workout>>() {
            @Override
            public void onChanged(@Nullable final List<Workout> workouts) {
                if(firstTimePopulated) {
                    populateWorkoutsFragment(inflater,container,workouts);
                    firstTimePopulated = false;
                } else {
                    View cardviewWorkout = (View) inflater.inflate(R.layout.cardview_workout, container ,false);
                    workoutTitle = cardviewWorkout.findViewById(R.id.workoutTitle);
                    workoutMuscle = cardviewWorkout.findViewById(R.id.workoutMuscles);
                    workoutTitle.setText(workouts.get(workouts.size()-1).getWorkoutName());
                    workoutMuscle.setText(workouts.get(workouts.size()-1).getMuscleGroup());
                    workoutsLayout.addView(cardviewWorkout);
                }
            }
        });

        exerciseRepository = ExerciseRepository.getInstance();

        return view;
    }

    void populateWorkoutsFragment(LayoutInflater inflater, ViewGroup container, List<Workout> workouts) {
        for(Workout workout:workouts) {
            View cardviewWorkout = (View) inflater.inflate(R.layout.cardview_workout, container ,false);
            workoutTitle = cardviewWorkout.findViewById(R.id.workoutTitle);
            workoutMuscle = cardviewWorkout.findViewById(R.id.workoutMuscles);
            workoutTitle.setText(workout.getWorkoutName());
            workoutMuscle.setText(workout.getMuscleGroup());
            workoutsLayout.addView(cardviewWorkout);
        }
    }

    public void getExercisesFromAPI() {
        exerciseRepository.getExercises(new OnGetAPIResponseCallBack() {
            @Override
            public void onSuccess(List<Result> exerciseListFromAPI) {
                exercises = mapResultToExercise(exerciseListFromAPI);
                getFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, new ChooseExerciseFragment(exercises))
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
}