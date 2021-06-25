package com.tfg.UIandroidGameEngine;

public class SetAutoMoveYAction  extends Action{
    public SetAutoMoveYAction(BasicGameObject executor, GameEngine theGameEngine) {
        super(executor, theGameEngine, "SetAutoMoveYAction");
    }

    @Override
    public void execute(int evenTrigger) {
        executor.speed = 0.5f;
    }
}
