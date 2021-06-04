package com.tfg.UIandroidGameEngine;

import android.widget.Toast;

public class DebugAction extends Action {

    public DebugAction(BasicGameObject executor, GameEngine theGameEngine){
        super(executor, theGameEngine);

    }

    @Override
    public void execute(int evenTrigger) {
        Toast.makeText(theGameEngine.ctx,"ACCION EJECUTADA",Toast.LENGTH_SHORT).show();
    }
}
