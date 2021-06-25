package com.tfg.UIandroidGameEngine;

public class SetFollowCameraAction extends  Action {
    public SetFollowCameraAction(BasicGameObject executor, GameEngine theGameEngine) {
        super(executor, theGameEngine, "SetFollowCameraAction");
    }

    @Override
    public void execute(int evenTrigger) {
        theGameEngine.camera.lookingAt = executor;
    }
}
