package com.tfg.UIandroidGameEngine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class CircleSprite implements Sprite {

    private Canvas renderCanvas;
    int[] colors = {Color.RED,Color.BLUE,Color.RED,Color.BLACK,Color.GREEN};
    int color = colors[0];
    public CircleSprite(){

    }

    @Override
    public void draw(Vector position, Vector scale, float rotation,Canvas renderCanvas, Camera camera) {
        Paint aux = new Paint();
        aux.setColor(color);
        renderCanvas.save();
        renderCanvas.rotate(rotation,position.x + camera.getScreenSpaceX(),position.y + camera.getScreenSpaceY());
        renderCanvas.drawCircle( position.x + camera.getScreenSpaceX()  ,position.y + camera.getScreenSpaceY(), 25*scale.y, aux);




        renderCanvas.restore();
    }
@Override
    public void loadSprite(int spriteNumber){
        color = colors[spriteNumber];
    }
}
