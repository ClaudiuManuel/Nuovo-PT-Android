package com.example.nuovo_pt.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.nuovo_pt.R;
import com.example.nuovo_pt.db.WorkoutViewModel;
import com.example.nuovo_pt.db.workouts.Workout;
import com.example.nuovo_pt.db.workouts.WorkoutFirebase;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewWorkoutFragment extends Fragment implements View.OnClickListener{
    String selectedMuscleGroup;
    EditText workoutNameEditText;
    private Button addNewWorkoutButton;
    private Button cancelNewWorkoutButton;
    WorkoutViewModel workoutViewModel;
    String clientName;
    private DatabaseReference databaseReference;

    public NewWorkoutFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clientName = getArguments().getString("clientName");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_workout, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference("Workouts");
        Spinner spinner = (Spinner) view.findViewById(R.id.muscle_groups_spinner);
        addNewWorkoutButton = view.findViewById(R.id.confirm_workout_addition);
        cancelNewWorkoutButton = view.findViewById(R.id.cancel_workout_addition);
        cancelNewWorkoutButton.setOnClickListener(this);
        addNewWorkoutButton.setOnClickListener(this);
        workoutNameEditText = view.findViewById(R.id.workoutNameEditText);

        workoutViewModel = new ViewModelProvider(this).get(WorkoutViewModel.class);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.muscle_groups_array, android.R.layout.simple_spinner_item);

        AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                ((TextView) parent.getChildAt(0)).setTextColor(ResourcesCompat.getColor(getResources(), R.color.secondaryColor, null));
                ((TextView) parent.getChildAt(0)).setTextSize(50);
                selectedMuscleGroup = ((TextView) parent.getChildAt(0)).getText().toString();

            }

            public void onNothingSelected(AdapterView<?> parent) {
                selectedMuscleGroup = ((TextView) parent.getChildAt(0)).getText().toString();
            }
        };
        spinner.setOnItemSelectedListener(onItemSelectedListener);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v == addNewWorkoutButton) {
            String workoutName = workoutNameEditText.getText().toString();
            if(workoutName.length() > 0) {
//                workoutViewModel.insert(new Workout(workoutName, selectedMuscleGroup, clientName));
                String workoutID = databaseReference.push().getKey();
                WorkoutFirebase workoutFirebase = new WorkoutFirebase(workoutID,workoutName,selectedMuscleGroup,clientName);
                databaseReference.child(workoutID).setValue(workoutFirebase);
                workoutNameEditText.setText("");
                Toast feedback = Toast.makeText(getContext(), "Workout added successfully:  " + workoutName, Toast.LENGTH_LONG);
                feedback.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 230);
                feedback.show();
            }
        } else if(v == cancelNewWorkoutButton) {
            getActivity().onBackPressed();
        }
    }
}
