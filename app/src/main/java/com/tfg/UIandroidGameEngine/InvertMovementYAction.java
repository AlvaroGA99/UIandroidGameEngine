package com.tfg.UIandroidGameEngine;

public class InvertMovementYAction extends Action {
    public InvertMovementYAction(BasicGameObject executor, GameEngine theGameEngine) {
        super(executor, theGameEngine, "InvertMovementYAction");
    }

    @Override
    public void execute(int evenTrigger) {
        executor.invertMovementY *= -1;

    }
}
