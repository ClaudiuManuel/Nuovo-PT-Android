package com.example.nuovo_pt.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.nuovo_pt.Client;
import com.example.nuovo_pt.ClientsAdditionListener;
import com.example.nuovo_pt.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class ClientWorkoutsFragment extends Fragment {
    Client client;
    LinearLayout workoutsLayout;
    CardView workoutCardView;
    TextView workoutTitle;

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
        for(int i=0;i<3;i++) {
            View cardviewWorkout = (View) inflater.inflate(R.layout.cardview_workout, container ,false);
            workoutTitle = cardviewWorkout.findViewById(R.id.workoutTitle);
            workoutTitle.setText(client.getName());
            workoutsLayout.addView(cardviewWorkout);
        }


        return view;
    }
}