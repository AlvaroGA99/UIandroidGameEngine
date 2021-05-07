package com.tfg.UIandroidGameEngine;

import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.constraintlayout.solver.widgets.Rectangle;

public class RectangleSprite implements  Sprite{



    public RectangleSprite(){

    }

    @Override
    public void draw(Vector position, Vector scale, float rotation,Canvas renderCanvas , Camera camera) {
        Paint aux = new Paint();
        aux.setARGB(255,255 , 0 ,0);
        renderCanvas.drawRect( position.x + camera.getScreenSpaceX()  - 25*scale.x,position.y + camera.getScreenSpaceY() - 25*scale.y, position.x + camera.getScreenSpaceX() + 25*scale.x, position.y + camera.getScreenSpaceY()+ 25*scale.y, aux);
    }
}
