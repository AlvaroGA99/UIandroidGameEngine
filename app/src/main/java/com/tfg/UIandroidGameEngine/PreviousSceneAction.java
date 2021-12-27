package com.tfg.UIandroidGameEngine;

public class PreviousSceneAction  extends  Action{
    public PreviousSceneAction(BasicGameObject executor, GameEngine theGameEngine) {
        super(executor, theGameEngine, "PreviousSceneAction");
    }

    @Override
    public void execute(int evenTrigger) {
        int sceneGo = theGameEngine.getCurrentScene() - 1;
        if(sceneGo < theGameEngine.SceneList.size() && sceneGo >= 0){
            theGameEngine.loadScene(sceneGo);
        }
    }
}
