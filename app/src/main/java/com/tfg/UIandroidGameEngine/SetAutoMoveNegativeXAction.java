package com.tfg.UIandroidGameEngine;

public class SetAutoMoveNegativeXAction extends  Action{
    public SetAutoMoveNegativeXAction(BasicGameObject executor, GameEngine theGameEngine) {
        super(executor, theGameEngine, "SetAutoMoveNegativeXAction");
    }

    @Override
    public void execute(int evenTrigger) {
            executor.speedx = -0.5f;
    }
}
