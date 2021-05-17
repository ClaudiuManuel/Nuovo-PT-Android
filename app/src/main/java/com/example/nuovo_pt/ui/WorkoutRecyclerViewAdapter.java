package com.example.nuovo_pt.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nuovo_pt.R;
import com.example.nuovo_pt.db.clients.ClientFirebase;
import com.example.nuovo_pt.db.workouts.WorkoutFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class WorkoutRecyclerViewAdapter extends RecyclerView.Adapter<WorkoutRecyclerViewAdapter.WorkoutViewHolder> {

    private ItemClickListener mClickListener;
    private LongItemClickListener longItemClickListener;

    class WorkoutViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        TextView workoutTitle,workoutLength,workoutLevel,workoutDate;
        Button addToFavorites,deleteWorkout;
        ImageView workoutIcon;
        private DatabaseReference databaseReference;

        public WorkoutViewHolder(View itemView) {
            super(itemView);
            databaseReference = FirebaseDatabase.getInstance().getReference("Workouts");

            workoutTitle = itemView.findViewById(R.id.workoutTitle);
            workoutLevel = itemView.findViewById(R.id.workoutLevelTextView);
            workoutLength = itemView.findViewById(R.id.workoutLengthTextView);
            workoutDate = itemView.findViewById(R.id.workoutDateTextview);
            workoutIcon = itemView.findViewById(R.id.workoutIcon);
            addToFavorites = itemView.findViewById(R.id.addToFavoritesButton);
            deleteWorkout = itemView.findViewById(R.id.deleteWorkoutButton);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            addToFavorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    WorkoutFirebase workoutFirebase = workouts.get(position);

                    if(workoutFirebase.getFavorite().equals("false")) {
                        databaseReference.child(workoutFirebase.getWorkoutID()+"/favorite").setValue("true");
                        addToFavorites.setBackgroundResource(R.drawable.favorite_workout_filled);
                    } else {
                        databaseReference.child(workoutFirebase.getWorkoutID()+"/favorite").setValue("false");
                        addToFavorites.setBackgroundResource(R.drawable.favorite_workout_border);
                    }
                }
            });

            deleteWorkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    WorkoutFirebase workoutFirebase = workouts.get(position);
                    new AlertDialog.Builder(context)
                            .setTitle("Workout removal")
                            .setMessage("Are you sure you want to remove this workout?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    databaseReference.child(workoutFirebase.getWorkoutID()).removeValue();
                                }
                            }).setNegativeButton("No", null).show();
                }
            });
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            if (longItemClickListener != null) longItemClickListener.onLongItemClick(v, getAdapterPosition());
            return true;
        }
    }

    private List<WorkoutFirebase> workouts;
    private LayoutInflater mInflater;
    private Context context;

    WorkoutRecyclerViewAdapter(Context context, List<WorkoutFirebase> workouts) {
        this.workouts = workouts;
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    @NonNull
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cardview_workout, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        holder.workoutTitle.setText(workouts.get(position).getWorkoutName());
        holder.workoutLevel.setText(workouts.get(position).getWorkoutLevel());
        holder.workoutLength.setText(workouts.get(position).getWorkoutLength());
        holder.workoutDate.setText(workouts.get(position).getWorkoutDate());

        if(workouts.get(position).getFavorite().equals("true")) {
            holder.addToFavorites.setBackgroundResource(R.drawable.favorite_workout_filled);
        }

        String workoutMuscle = workouts.get(position).getMuscleTargeted();

        switch (workoutMuscle) {
            case "Back":
                holder.workoutIcon.setImageResource(R.drawable.back_primary_color);
                break;
            case "Legs":
                holder.workoutIcon.setImageResource(R.drawable.legs_primary_color);
                break;
            case "Arms":
                holder.workoutIcon.setImageResource(R.drawable.arms_primary_color);
                break;
            case "Abs":
                holder.workoutIcon.setImageResource(R.drawable.abs_primary_color);
                break;
            case "Shoulders":
                holder.workoutIcon.setImageResource(R.drawable.shoulders_primary_color);
                break;
            case "Calves":
                holder.workoutIcon.setImageResource(R.drawable.calves_first_option);
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (workouts != null)
            return workouts.size();
        else return 0;
    }

    public void setClients(List<WorkoutFirebase> workouts) {
        this.workouts = workouts;
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    void setLongClickListener(LongItemClickListener longItemClickListener) {
        this.longItemClickListener = longItemClickListener;
    }

    WorkoutFirebase getWorkout(int position) {
        return workouts.get(position);
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface LongItemClickListener {
        void onLongItemClick(View view, int position);
    }
}
