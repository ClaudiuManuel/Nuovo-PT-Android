package com.example.nuovo_pt.db.clients;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.nuovo_pt.db.clients.Client;

import java.util.List;

@Dao
public interface ClientDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Client client);

//    @Query("DELETE FROM clients")
//    void deleteAll();

    @Query("SELECT * from clients ORDER BY name ASC")
    LiveData<List<Client>> getAlphabetizedClients();
}
