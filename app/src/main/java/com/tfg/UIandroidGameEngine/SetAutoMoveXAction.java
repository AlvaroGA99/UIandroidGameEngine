package com.tfg.UIandroidGameEngine;

public class SetAutoMoveXAction extends  Action {
    public SetAutoMoveXAction(BasicGameObject executor, GameEngine theGameEngine) {
        super(executor, theGameEngine, "SetAutoMoveXAction");
    }

    @Override
    public void execute(int evenTrigger) {
        executor.speedx = 0.5f;
    }
}
