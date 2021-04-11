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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class SavedWorkouts extends Fragment implements WorkoutRecyclerViewAdapter.ItemClickListener{

    NavController navController = null;
    List<WorkoutFirebase> workouts = new ArrayList<>();
    private DatabaseReference databaseReference;

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
        Bundle bundle = new Bundle();
        bundle.putString("workoutID",adapter.getWorkout(position).getWorkoutID());
        navController.navigate(R.id.action_clientWorkoutsFragment_to_workoutExercisesFragment2,bundle);
    }
}