package com.tfg.UIandroidGameEngine;

public abstract class Action {

    protected BasicGameObject executor;

    protected GameEngine theGameEngine;

    public String name;

    public Action(BasicGameObject executor, GameEngine theGameEngine, String name){
    this.name = name;
        this.executor = executor;
        this.theGameEngine = theGameEngine;

    }

   public abstract void execute( int evenTrigger); //-1 if the system is the eventTrigger, sceneHierarchyId otherwise
}
