package com.example.nuovo_pt.db.clients;

public class ClientFirebase {
    String name;
    boolean isMale;
    String userEmail;

    public ClientFirebase() {

    }

    public ClientFirebase (String name,boolean isMale, String userEmail) {
        this.name = name;
        this.isMale = isMale;
        this.userEmail = userEmail;
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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

}
