package com.example.nuovo_pt.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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

import com.example.nuovo_pt.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


public class UpdateWorkout extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener{

    String selectedMuscleGroup;
    EditText workoutNameEditText;
    private Button updateWorkoutButton;
    private Button cancelWorkoutUpdate;
    String clientName,targetedMuscle,workoutName,workoutDate,workoutLength,workoutLevel,workoutID;
    private DatabaseReference databaseReference;
    EditText dateEditText, levelEditText, workoutLengthEditText;
    int currentDay,currentMonth,currentYear;
    Spinner spinner;

    public UpdateWorkout() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clientName = getArguments().getString("clientName");
        targetedMuscle = getArguments().getString("muscleTargeted");
        workoutName = getArguments().getString("workoutName");
        workoutLevel = getArguments().getString("workoutLevel");
        workoutLength = getArguments().getString("workoutLength");
        workoutDate = getArguments().getString("workoutDate");
        workoutID = getArguments().getString("workoutID");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_workout, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference("Workouts");
        spinner = (Spinner) view.findViewById(R.id.update_muscle_groups_spinner);
        updateWorkoutButton = view.findViewById(R.id.confirm_workout_update);
        levelEditText = view.findViewById(R.id.update_editTextLevel);
        workoutLengthEditText = view.findViewById(R.id.update_editTextWorkoutLength);
        dateEditText = view.findViewById(R.id.update_editTextDate);
        dateEditText.setOnClickListener(this);
        cancelWorkoutUpdate = view.findViewById(R.id.cancel_workout_update);
        cancelWorkoutUpdate.setOnClickListener(this);
        updateWorkoutButton.setOnClickListener(this);
        workoutNameEditText = view.findViewById(R.id.update_editTextWorkoutName);

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

        setFieldsValues();

        return view;
    }

    private void setFieldsValues() {
        workoutNameEditText.setText(workoutName);
        workoutLengthEditText.setText(workoutLength);
        levelEditText.setText(workoutLevel);
        dateEditText.setText(workoutDate);
        spinner.setSelection(((ArrayAdapter)spinner.getAdapter()).getPosition(targetedMuscle));
    }

    @Override
    public void onClick(View v) {
        if(v == updateWorkoutButton) {
            String updatedWorkoutName = workoutNameEditText.getText().toString();
            String updatedWorkoutDate = dateEditText.getText().toString();
            String updatedWorkoutLevel = levelEditText.getText().toString();
            String updatedWorkoutLength = workoutLengthEditText.getText().toString();
            if(updatedWorkoutName.length() > 0 && updatedWorkoutDate.length() > 0 && updatedWorkoutLevel.length() > 0 && updatedWorkoutLength.length() > 0) {
                databaseReference.child(workoutID).child("muscleTargeted").setValue(selectedMuscleGroup);
                databaseReference.child(workoutID).child("workoutDate").setValue(updatedWorkoutDate);
                databaseReference.child(workoutID).child("workoutLength").setValue(updatedWorkoutLength);
                databaseReference.child(workoutID).child("workoutLevel").setValue(updatedWorkoutLevel);
                databaseReference.child(workoutID).child("workoutName").setValue(updatedWorkoutName);
                Toast feedback = Toast.makeText(getContext(), "Workout updated successfully:  " + updatedWorkoutName, Toast.LENGTH_LONG);
                feedback.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 230);
                feedback.show();
                getActivity().onBackPressed();
            }
        } else if(v == cancelWorkoutUpdate) {
            getActivity().onBackPressed();
        } else if(v == dateEditText) {
            if(currentDay == 0 || currentMonth == 0 || currentYear == 0) {
                currentMonth = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
                currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
                currentDay = java.util.Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
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