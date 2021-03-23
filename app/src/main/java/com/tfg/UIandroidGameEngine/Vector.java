package com.tfg.UIandroidGameEngine;

public class Vector {

    public float x;
    public float y;

    public Vector(float x, float y){
        this.x = x;
        this.y = y;
    }


    public double magnitude(){
        return   Math.sqrt(x*x + y*y);
    }

    public float dotProduct(Vector other){
        return this.x*other.x + this.y*other.y;
    }

    public Vector normalized(){
        return new Vector((float  ) (this.x/this.magnitude()),(float)(this.y/this.magnitude()));

    }

    public void add(Vector toAdd){
           this.x += toAdd.x;
           this.y += toAdd.y;

    }
}
