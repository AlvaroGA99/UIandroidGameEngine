package com.tfg.UIandroidGameEngine;

public class Project {

    private String key;
    private String user;

    private ProjectData project;

    public Project(String key, String user, ProjectData project){
        this.key = key;
        this.user = user;
        this.project = project;
    }

    public ProjectData getProject() {
        return project;
    }

    public String getKey() {
        return key;
    }

    public String getUser() {
        return user;
    }
}
