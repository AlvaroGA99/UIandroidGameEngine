package com.tfg.UIandroidGameEngine;

public class Project {

    private String key;
    private String user;
    private String name;
    private String type;

    public Project(String key, String user, String name,String type){
        this.key = key;
        this.user = user;
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getUser() {
        return user;
    }


    public String getType(){return  type;}
}
