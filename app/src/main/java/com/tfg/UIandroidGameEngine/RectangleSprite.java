package com.tfg.UIandroidGameEngine;

import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.constraintlayout.solver.widgets.Rectangle;

public class RectangleSprite implements  Sprite{



    public RectangleSprite(){

    }

    @Override
    public void draw(Vector position, Vector scale, float rotation,Canvas renderCanvas) {
        Paint aux = new Paint();
        aux.setARGB(255,255 , 0 ,0);
        renderCanvas.drawRect(position.x,position.y, position.x + 50*scale.x, position.y + 50*scale.y, aux);
    }
}
