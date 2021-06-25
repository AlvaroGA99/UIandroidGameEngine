package com.tfg.UIandroidGameEngine;

public class ResetYAction  extends  Action{
    public ResetYAction(BasicGameObject executor, GameEngine theGameEngine) {
        super(executor, theGameEngine, "ResetXAction");
    }

    @Override
    public void execute(int evenTrigger) {
        executor.preUpdatePosition.y = executor.startPosition.y;
    }
}
