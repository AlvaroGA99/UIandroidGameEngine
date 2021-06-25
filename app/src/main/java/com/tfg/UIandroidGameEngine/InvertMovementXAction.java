package com.tfg.UIandroidGameEngine;

public class InvertMovementXAction extends  Action {
    public InvertMovementXAction(BasicGameObject executor, GameEngine theGameEngine) {
        super(executor, theGameEngine, "InvertMovementXAction");
    }

    @Override
    public void execute(int evenTrigger) {
        executor.invertMovementX *= -1;
    }
}
