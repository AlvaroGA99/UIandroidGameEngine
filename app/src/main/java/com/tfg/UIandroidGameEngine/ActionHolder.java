package com.tfg.UIandroidGameEngine;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ActionHolder {


    public ArrayList<Action>   onClickActions = new ArrayList<Action>();

    public ArrayList<ArrayList<Action>>  collisionActions = new ArrayList<ArrayList<Action>>();

    public ArrayList<Action> startSceneActions = new ArrayList<Action>();

    public ArrayList<Action> updateActions = new ArrayList<Action>();

    public ActionHolder(){

    }

}
