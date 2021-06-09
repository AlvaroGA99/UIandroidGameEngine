package com.tfg.UIandroidGameEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class ProjectData {

    private String title;
    private String projectType;
    private boolean published;
    private HashMap<String, ArrayList<String[]>> scenes;

    public ProjectData(){

    }

    public ProjectData (String title, String projectType, boolean published, HashMap<String,ArrayList<String[]>> scenes){
        this.title = title;
        this.projectType = projectType;
        this.published = published;
        this.scenes = scenes;
    }

    public String getProjectType() {
        return projectType;
    }

    public String getTitle() {
        return title;
    }

    public boolean isPublished() {
        return published;
    }

    public HashMap<String, ArrayList<String[]>> getScenes() {
        return scenes;
    }
}
