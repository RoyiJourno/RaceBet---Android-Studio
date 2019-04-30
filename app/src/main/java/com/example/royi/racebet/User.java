package com.example.royi.racebet;
import java.util.List;

public class User {
    private String name;
    private String phone;
    private String photoPath;
    private List<String> groupList;

    public User(String name,String phone,String PhotoPath,List<String> groupList){
        this.name = name;
        this.phone = phone;
        this.photoPath = PhotoPath;
        this.groupList = groupList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public List<String> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<String> groupList) {
        this.groupList = groupList;
    }
}
