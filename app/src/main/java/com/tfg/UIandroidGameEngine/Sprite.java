package com.tfg.UIandroidGameEngine;

import android.graphics.Canvas;

public interface Sprite {

     void draw(Vector position, Vector scale, float rotation, Canvas renderCanvas, Camera camera);
     void loadSprite(int spriteNumber);
}
