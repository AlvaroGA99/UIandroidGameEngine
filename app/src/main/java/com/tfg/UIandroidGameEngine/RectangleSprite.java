package com.tfg.UIandroidGameEngine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.constraintlayout.solver.widgets.Rectangle;

public class RectangleSprite implements  Sprite{

    int[] colors = {Color.RED,Color.BLUE,Color.RED,Color.BLACK,Color.GREEN};
    int color = colors[0];

    public RectangleSprite(){

    }

    @Override
    public void draw(Vector position, Vector scale, float rotation,Canvas renderCanvas , Camera camera) {
        Paint aux = new Paint();
        aux.setColor(color);
        renderCanvas.save();
        renderCanvas.rotate(rotation,position.x + camera.getScreenSpaceX(),position.y + camera.getScreenSpaceY());
        renderCanvas.drawRect( position.x + camera.getScreenSpaceX()  - 25*scale.x,position.y + camera.getScreenSpaceY() - 25*scale.y, position.x + camera.getScreenSpaceX() + 25*scale.x, position.y + camera.getScreenSpaceY()+ 25*scale.y, aux);




        renderCanvas.restore();
    }

    @Override
    public void loadSprite(int spriteNumber){
        color = colors[spriteNumber];
    }
}
