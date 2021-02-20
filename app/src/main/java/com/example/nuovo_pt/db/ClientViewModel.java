package com.example.nuovo_pt.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.nuovo_pt.db.clients.Client;

import java.util.List;

public class ClientViewModel extends AndroidViewModel {

    private ClientRepository clientRepository;
    private LiveData<List<Client>> allClients;

    public ClientViewModel(@NonNull Application application) {
        super(application);
        clientRepository = new ClientRepository(application);
        allClients = clientRepository.getAllClients();
    }

    public LiveData<List<Client>> getAllClients() {
        return allClients;
    }

    public void insert(Client client) {
        clientRepository.insert(client);
    }
}
