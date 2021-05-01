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
import com.example.nuovo_pt.db.workouts.WorkoutFirebase;

import java.util.List;

public class CalendarWorkoutRecyclerViewAdapter extends RecyclerView.Adapter<CalendarWorkoutRecyclerViewAdapter.CalendarWorkoutViewHolder> {
    private WorkoutRecyclerViewAdapter.ItemClickListener mClickListener;

    class CalendarWorkoutViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView workoutTitle,workoutLength,workoutLevel,clientName;
        ImageView workoutIcon;

        public CalendarWorkoutViewHolder(View itemView) {
            super(itemView);

            workoutTitle = itemView.findViewById(R.id.calendar_workoutTitle);
            workoutLevel = itemView.findViewById(R.id.calendar_workoutLevelTextView);
            workoutLength = itemView.findViewById(R.id.calendar_workoutLengthTextView);
            clientName = itemView.findViewById(R.id.calendarClientName);
            workoutIcon = itemView.findViewById(R.id.calendar_workoutIcon);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    private List<WorkoutFirebase> workouts;
    private LayoutInflater mInflater;
    private Context context;

    CalendarWorkoutRecyclerViewAdapter(Context context, List<WorkoutFirebase> workouts) {
        this.workouts = workouts;
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    @NonNull
    public CalendarWorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.workout_calendar, parent, false);
        return new CalendarWorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarWorkoutViewHolder holder, int position) {
        holder.workoutTitle.setText(workouts.get(position).getWorkoutName());
        holder.workoutLevel.setText(workouts.get(position).getWorkoutLevel());
        holder.workoutLength.setText(workouts.get(position).getWorkoutLength());
        holder.clientName.setText(workouts.get(position).getClientsName());

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

    void setClickListener(WorkoutRecyclerViewAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public void setWorkouts(List<WorkoutFirebase> workouts) {
        this.workouts = workouts;
    }

    WorkoutFirebase getWorkout(int position) {
        return workouts.get(position);
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
