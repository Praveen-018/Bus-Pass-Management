package com.example.buspass;

public class Users {
    private final String date;
    private String Name, userName, ph, sour, desti;
    private String validity; // Add validity field

    public Users(String name, String userName, String ph, String sour, String desti) {
        this.Name = name;
        this.userName = userName;
        this.ph = ph;
        this.sour = sour;
        this.desti = desti;
        this.validity = "0"; // Set validity to 0
        this.date = null;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getSour() {
        return sour;
    }

    public void setSour(String sour) {
        this.sour = sour;
    }

    public String getDesti() {
        return desti;
    }

    public void setDesti(String desti) {
        this.desti = desti;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }
}
