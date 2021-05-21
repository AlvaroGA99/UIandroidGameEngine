package com.tfg.UIandroidGameEngine;

public interface Action {

    void execute(BasicGameObject executer, int evenTrigger); //-1 if the system is the eventTrigger, sceneHierarchyId otherwise
}
