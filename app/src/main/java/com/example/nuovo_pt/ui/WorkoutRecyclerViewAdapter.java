package com.example.nuovo_pt.ui;

import android.content.Context;
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

    class WorkoutViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView workoutTitle,workoutLength,workoutLevel,workoutDate;
        Button addToFavorites;
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
            itemView.setOnClickListener(this);

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
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    private List<WorkoutFirebase> workouts;
    private LayoutInflater mInflater;

    WorkoutRecyclerViewAdapter(Context context, List<WorkoutFirebase> workouts) {
        this.workouts = workouts;
        mInflater = LayoutInflater.from(context);
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

    WorkoutFirebase getWorkout(int position) {
        return workouts.get(position);
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
