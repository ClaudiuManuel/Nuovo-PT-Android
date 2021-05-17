package com.example.nuovo_pt.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nuovo_pt.R;
import com.example.nuovo_pt.db.workouts.WorkoutFirebase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClientWorkoutsFragment extends Fragment implements View.OnClickListener, WorkoutRecyclerViewAdapter.ItemClickListener, WorkoutRecyclerViewAdapter.LongItemClickListener {
    String clientName;
    NavController navController = null;
    List<WorkoutFirebase> workouts = new ArrayList<>();
    FloatingActionButton fab;
    private DatabaseReference databaseReference;

    WorkoutRecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    public ClientWorkoutsFragment() {

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
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(clientName);

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_client_workouts, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference("Workouts");

        recyclerView = view.findViewById(R.id.workoutsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    void populateWorkoutsFragment(List<WorkoutFirebase> workouts) {
        adapter = new WorkoutRecyclerViewAdapter(mContext, workouts);
        adapter.setClickListener(this);
        adapter.setLongClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if(v != null && v==fab) {
            final String[] options = {
                    "Add from saved workouts", "Create new workout"
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Select workout building method");
            builder.setItems(options, new DialogInterface.OnClickListener() {@
                    Override
            public void onClick(DialogInterface dialog, int which) {
                if ("Add from saved workouts".equals(options[which])) {
                    Bundle bundle = new Bundle();
                    bundle.putString("clientName",clientName);
                    navController.navigate(R.id.action_clientWorkoutsFragment_to_savedWorkouts,bundle);
                } else if ("Create new workout".equals(options[which])) {
                    Bundle bundle = new Bundle();
                    bundle.putString("clientName",clientName);
                    navController.navigate(R.id.action_clientWorkoutsFragment_to_newWorkoutFragment2,bundle);
                }
            }
            });
            builder.show();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("workoutID",adapter.getWorkout(position).getWorkoutID());
        navController.navigate(R.id.action_clientWorkoutsFragment_to_workoutExercisesFragment2,bundle);
    }

    @Override
    public void onLongItemClick(View view, int position) {
        WorkoutFirebase workoutFirebase = adapter.getWorkout(position);

        Bundle bundle = new Bundle();
        bundle.putString("clientName",clientName);
        bundle.putString("muscleTargeted",workoutFirebase.getMuscleTargeted());
        bundle.putString("workoutDate",workoutFirebase.getWorkoutDate());
        bundle.putString("workoutLength",workoutFirebase.getWorkoutLength());
        bundle.putString("workoutLevel",workoutFirebase.getWorkoutLevel());
        bundle.putString("workoutName",workoutFirebase.getWorkoutName());
        bundle.putString("workoutID",workoutFirebase.getWorkoutID());
        navController.navigate(R.id.action_clientWorkoutsFragment_to_updateWorkout,bundle);
    }
}