package com.example.nuovo_pt.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.nuovo_pt.db.clients.Client;
import com.example.nuovo_pt.db.clients.ClientDao;

import java.util.List;

public class ClientRepository {
    private ClientDao clientDao;
    private LiveData<List<Client>> allClients;

    ClientRepository(Application application) {
        NuovoRoomDatabase db = NuovoRoomDatabase.getDatabase(application);
        clientDao = db.clientDao();
        allClients = clientDao.getAlphabetizedClients();
    }

    LiveData<List<Client>> getAllClients() {
        return allClients;
    }

    void insert(Client client) {
        NuovoRoomDatabase.databaseWriteExecutor.execute(() -> {
            clientDao.insert(client);
        });
    }
}
