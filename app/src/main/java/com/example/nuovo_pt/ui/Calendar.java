package com.example.nuovo_pt.ui;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nuovo_pt.R;
import com.example.nuovo_pt.db.workouts.WorkoutFirebase;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Calendar extends Fragment {
    CompactCalendarView compactCalendarView;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private DatabaseReference databaseReference;

    List<WorkoutFirebase> workoutsForSpecificDay = new ArrayList<>();
    List<WorkoutFirebase> workouts = new ArrayList<>();
    CalendarWorkoutRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    boolean firstTimeShowing = true;

    public Calendar() {
        // Required empty public constructor
    }

    private Context mContext;

    // Initialise it from onAttach()
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
                    workouts.add(workoutFirebase);
                }

                populateCalendar(workouts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void populateCalendar(List<WorkoutFirebase> workouts) {
        for(WorkoutFirebase workout:workouts) {
            Event event = new Event(Color.parseColor("#fca311"), getDateInEpoch(workout.getWorkoutDate()),workout);
            compactCalendarView.addEvent(event);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        compactCalendarView = view.findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        compactCalendarView.setCurrentDate(new Date());

        databaseReference = FirebaseDatabase.getInstance().getReference("Workouts");

        recyclerView = view.findViewById(R.id.calendar_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //COMPACTCALENDARCONTROLLER (I'll need to fork the project and modify line 207 in that class so I can increase the event dot radius)
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                workoutsForSpecificDay.clear();
                for(Event event:events) {
                    workoutsForSpecificDay.add((WorkoutFirebase)event.getData());
                }

                if(firstTimeShowing) {
                    adapter = new CalendarWorkoutRecyclerViewAdapter(mContext,workoutsForSpecificDay);
//                  adapter.setClickListener(this);
                    recyclerView.setAdapter(adapter);
                    firstTimeShowing = false;
                } else {
                    adapter.setWorkouts(workoutsForSpecificDay);
                    adapter.notifyDataSetChanged();
                }

                Log.d("Events", "Day was clicked: " + dateClicked + " with events " + events);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });
        return view;
    }

    private long getDateInEpoch(String dateString) {
        SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy");
        Date date = null;
        try {
            date = df.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long epoch = date.getTime();
        return epoch;
    }
}