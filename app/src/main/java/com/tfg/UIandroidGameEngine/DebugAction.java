package com.tfg.UIandroidGameEngine;

import android.app.Application;
import android.widget.Toast;

public class DebugAction extends Action {

    public DebugAction(BasicGameObject executor, GameEngine theGameEngine){
        super(executor, theGameEngine,"DebugAction");

    }

    @Override
    public void execute(int evenTrigger) {
        theGameEngine.deleteQueue.add(executor);
    }
}
