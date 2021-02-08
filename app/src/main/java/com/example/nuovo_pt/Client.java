package com.example.nuovo_pt;

public class Client {

    private String name;
    private boolean isMale=true;

    public Client (String name,boolean isMale) {
        this.name = name;
        this.isMale = isMale;
    }

    public String getName() {
        return name;
    }

    public boolean getSex () {
        return isMale;
    }
}
