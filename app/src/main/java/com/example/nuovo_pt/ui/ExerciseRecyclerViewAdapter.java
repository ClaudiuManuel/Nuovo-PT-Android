package com.example.nuovo_pt.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nuovo_pt.R;
import com.example.nuovo_pt.db.clients.ClientFirebase;
import com.example.nuovo_pt.db.exercises.ExerciseFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ExerciseRecyclerViewAdapter extends RecyclerView.Adapter<ExerciseRecyclerViewAdapter.ExerciseViewHolder>{

    private ItemClickListener mClickListener;

    class ExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView exerciseTitle,exerciseMuscle;
        private DatabaseReference databaseReference;

        public ExerciseViewHolder(View itemView) {
            super(itemView);
            databaseReference = FirebaseDatabase.getInstance().getReference("Exercises");

            exerciseTitle = itemView.findViewById(R.id.exercise_title);
            exerciseMuscle = itemView.findViewById(R.id.exercise_muscle);
            itemView.setOnClickListener(this);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    ExerciseFirebase exerciseFirebase = exercises.get(getAdapterPosition());
                    new AlertDialog.Builder(context)
                            .setTitle("Exercise removal")
                            .setMessage("Are you sure you want to remove this exercise?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    databaseReference.child(exerciseFirebase.getExerciseID()).removeValue();
                                }
                            }).setNegativeButton("No", null).show();
                    return true;
                }
            });
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }

    }

    private List<ExerciseFirebase> exercises;
    private LayoutInflater mInflater;
    private Context context;

    ExerciseRecyclerViewAdapter(Context context,List<ExerciseFirebase> exercises) {
        this.exercises = exercises;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    @NonNull
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.male_client_cardview, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        holder.exerciseTitle.setText(exercises.get(position).getExerciseName());
        holder.exerciseMuscle.setText(exercises.get(position).getMuscleTargeted());
    }

    @Override
    public int getItemCount() {
        if (exercises != null)
            return exercises.size();
        else return 0;
    }

    public void setExercises(List<ExerciseFirebase> exercises) {
        this.exercises = exercises;
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    ExerciseFirebase getExercise(int position) {
        return exercises.get(position);
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
