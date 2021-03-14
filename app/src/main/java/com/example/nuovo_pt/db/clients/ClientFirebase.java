package com.example.nuovo_pt.db.clients;

import java.util.ArrayList;

public class ClientFirebase {
    String name;
    boolean isMale;

    public ClientFirebase() {

    }

    public ClientFirebase (String name,boolean isMale) {
        this.name = name;
        this.isMale = isMale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsMale() {
        return isMale;
    }

    public void setIsMale(boolean isMale) {
        this.isMale = isMale;
    }

}
