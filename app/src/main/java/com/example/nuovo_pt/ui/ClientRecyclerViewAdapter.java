package com.example.nuovo_pt.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nuovo_pt.OnItemClickListener;
import com.example.nuovo_pt.R;
import com.example.nuovo_pt.db.clients.Client;
import com.example.nuovo_pt.db.exercises.Exercise;

import java.util.List;

public class ClientRecyclerViewAdapter extends RecyclerView.Adapter<ClientRecyclerViewAdapter.ClientViewHolder> {

    private ItemClickListener mClickListener;

    class ClientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView clientNameTextview;
        private final ImageView clientIcon;

        public ClientViewHolder(View itemView) {
            super(itemView);
            clientNameTextview = itemView.findViewById(R.id.male_cardview_text);
            clientIcon = itemView.findViewById(R.id.client_cardview_icon);
            itemView.setOnClickListener(this);
        }

        public void bind(Exercise exercise, OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(exercise);
                }
            });
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    private List<Client> clients;
    private LayoutInflater mInflater;

    ClientRecyclerViewAdapter(Context context,List<Client> clients) {
        this.clients = clients;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    @NonNull
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.male_client_cardview, parent, false);
        return new ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {
        holder.clientNameTextview.setText(clients.get(position).getName());
        if(clients.get(position).getIsMale() == 0)
            holder.clientIcon.setImageResource(R.drawable.female);
    }

    @Override
    public int getItemCount() {
        if (clients != null)
            return clients.size();
        else return 0;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    Client getClient(int position) {
        return clients.get(position);
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
