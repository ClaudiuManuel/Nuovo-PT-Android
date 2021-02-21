package com.example.nuovo_pt.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nuovo_pt.R;
import com.example.nuovo_pt.api.ExerciseAdapter;
import com.example.nuovo_pt.db.exercises.Exercise;

import java.util.List;

public class ChooseExerciseFragment extends Fragment {
    private List<Exercise> exerciseList;

    public ChooseExerciseFragment(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_exercise, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.exercises_recyclerview);
        final ExerciseAdapter adapter = new ExerciseAdapter(this.getContext(),exerciseList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return view;
    }
}
