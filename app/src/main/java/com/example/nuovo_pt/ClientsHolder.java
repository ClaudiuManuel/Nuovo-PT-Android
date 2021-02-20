package com.example.nuovo_pt;

import com.example.nuovo_pt.db.clients.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientsHolder {

    List<Client> clients ;
    private static ClientsHolder clientsHolderInstance = null;

    private ClientsHolder() { }

    public static ClientsHolder getInstance() {
        if(clientsHolderInstance == null)
            clientsHolderInstance = new ClientsHolder();
        return clientsHolderInstance;
    }

    public void addNewClient(Client newClient) {
        if(clients == null)
            clients = new ArrayList<>();
        clients.add(newClient);
    }

    public List<Client> getClients() {
        if(clients == null)
            return new ArrayList<>();
        return clients;
    }
}
