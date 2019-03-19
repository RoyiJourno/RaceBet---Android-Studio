package com.example.royi.racebet;

public class Group {
    private String name;
    private String durtion;
    private String max_users;
    private String adminID;
    private String betPrice;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDurtion() {
        return durtion;
    }

    public void setDurtion(String durtion) {
        this.durtion = durtion;
    }

    public String getMax_users() {
        return max_users;
    }

    public void setMax_users(String max_users) {
        this.max_users = max_users;
    }

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public String getBetPrice() {
        return betPrice;
    }

    public void setBetPrice(String betPrice) {
        this.betPrice = betPrice;
    }

}
