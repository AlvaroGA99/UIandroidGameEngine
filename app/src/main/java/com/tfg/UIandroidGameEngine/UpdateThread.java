package com.tfg.UIandroidGameEngine;

public class UpdateThread extends Thread {

    private GameEngine theGameEngine;

    public UpdateThread(GameEngine gameEngine){
        this.theGameEngine = gameEngine;
    }

    @Override
    public void run(){
        while(true){
            theGameEngine.updateAll();
        }
    }

}
