package com.tfg.UIandroidGameEngine;

public class ResetXAction extends  Action {
    public ResetXAction(BasicGameObject executor, GameEngine theGameEngine) {
        super(executor, theGameEngine, "ResetXAction");
    }

    @Override
    public void execute(int evenTrigger) {
            executor.preUpdatePosition.x = executor.startPosition.x;
    }
}
