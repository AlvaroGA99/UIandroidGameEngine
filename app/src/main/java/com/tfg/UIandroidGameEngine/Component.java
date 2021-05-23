package com.tfg.UIandroidGameEngine;

public abstract class Component  {

    protected BasicGameObject container;

    public String name;

    public Component(BasicGameObject container, String name){
        this.container = container;
        this.name = name;
    }

    public abstract void process(long elapsedTime);



}
