package com.example.nuovo_pt.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.nuovo_pt.R;
import com.example.nuovo_pt.db.WorkoutViewModel;
import com.example.nuovo_pt.db.workouts.WorkoutFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class NewWorkoutFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    String selectedMuscleGroup;
    EditText workoutNameEditText;
    private Button addNewWorkoutButton;
    private Button cancelNewWorkoutButton;
    WorkoutViewModel workoutViewModel;
    String clientName;
    private DatabaseReference databaseReference;
    EditText dateEditText, levelEditText, workoutLengthEditText;
    int currentDay,currentMonth,currentYear;

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
        levelEditText = view.findViewById(R.id.editTextLevel);
        workoutLengthEditText = view.findViewById(R.id.editTextWorkoutLength);
        dateEditText = view.findViewById(R.id.editTextDate);
        dateEditText.setOnClickListener(this);
        cancelNewWorkoutButton = view.findViewById(R.id.cancel_workout_addition);
        cancelNewWorkoutButton.setOnClickListener(this);
        addNewWorkoutButton.setOnClickListener(this);
        workoutNameEditText = view.findViewById(R.id.editTextWorkoutName);

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
            String date = dateEditText.getText().toString();
            String level = levelEditText.getText().toString();
            String estimatedLength = workoutLengthEditText.getText().toString();
            if(workoutName.length() > 0 && date.length() > 0 && level.length() > 0 && estimatedLength.length() > 0) {
                String workoutID = databaseReference.push().getKey();
                WorkoutFirebase workoutFirebase = new WorkoutFirebase(workoutID,workoutName,estimatedLength,level, "false",date,selectedMuscleGroup,clientName);
                databaseReference.child(workoutID).setValue(workoutFirebase);
                workoutNameEditText.setText("");
                Toast feedback = Toast.makeText(getContext(), "Workout added successfully:  " + workoutName, Toast.LENGTH_LONG);
                feedback.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 230);
                feedback.show();
            }
        } else if(v == cancelNewWorkoutButton) {
            getActivity().onBackPressed();
        } else if(v == dateEditText) {
            if(currentDay == 0 || currentMonth == 0 || currentYear == 0) {
                currentMonth = Calendar.getInstance().get(Calendar.MONTH);
                currentYear = Calendar.getInstance().get(Calendar.YEAR);
                currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            }
            showDatePicker(currentDay, currentMonth, currentYear);
        }
    }

    private void showDatePicker(int day, int month, int year) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),this,
                year,
                month,
                day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        view.setMinDate(System.currentTimeMillis() - 1000);

        currentDay = dayOfMonth;
        currentYear = year;
        currentMonth = month;

        dateEditText.setText(getFormatedDate(year, month+1, dayOfMonth));
    }

    private String getFormatedDate(int year, int month, int dayOfMonth) {
        String monthString,dayString,yearString;
        yearString = year+"";

        if(dayOfMonth < 10) {
            dayString = "0" + dayOfMonth;
        } else
            dayString = dayOfMonth + "";

        switch(month) {
            case 1:
                monthString = "Jan";
                break;
            case 2:
                monthString = "Feb";
                break;
            case 3:
                monthString = "Mar";
                break;
            case 4:
                monthString = "Apr";
                break;
            case 5:
                monthString = "May";
                break;
            case 6:
                monthString = "Jun";
                break;
            case 7:
                monthString = "Jul";
                break;
            case 8:
                monthString = "Aug";
                break;
            case 9:
                monthString = "Sep";
                break;
            case 10:
                monthString = "Oct";
                break;
            case 11:
                monthString = "Nov";
                break;
            case 12:
                monthString = "Dec";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + month);
        }

        return monthString + " " + dayString + " " + yearString;
    }
}
