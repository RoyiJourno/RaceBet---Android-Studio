package com.example.royi.racebet;

import java.util.List;

public class Group {
    public String getGruopID() {
        return gruopID;
    }

    public void setGruopID(String gruopID) {
        this.gruopID = gruopID;
    }

    private String gruopID;
    private String name;
    private String durtion;
    private String max_users;
    private String adminID;
    private String betPrice;
    private List<User> groupUser;

    public Group(){

    }

    public Group(String gruopID,String name,String durtion,String max_users,String adminID,List<User> groupUser){
        this.gruopID=gruopID;
        this.name=name;
        this.durtion=durtion;
        this.max_users=max_users;
        this.adminID=adminID;
        this.groupUser=groupUser;

    }

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
