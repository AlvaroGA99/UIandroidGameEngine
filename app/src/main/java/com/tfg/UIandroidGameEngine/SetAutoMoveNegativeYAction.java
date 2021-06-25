package com.tfg.UIandroidGameEngine;

public class SetAutoMoveNegativeYAction extends  Action {
    public SetAutoMoveNegativeYAction(BasicGameObject executor, GameEngine theGameEngine) {
        super(executor, theGameEngine, "SetAutoMoveNegativeYAction");
    }

    @Override
    public void execute(int evenTrigger) {
        executor.speed = -0.5f;

    }
}
