package com.tfg.UIandroidGameEngine;

public class Vector {

    public double x;
    public double y;

    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }


    public double magnitude(){
        return   Math.sqrt(x*x + y*y);
    }

    public double dotProduct(Vector other){
        return this.x*other.x + this.y*other.y;
    }

    public Vector normalized(){
        return new Vector(this.x/this.magnitude(),this.y/this.magnitude());

    }

    public void add(Vector toAdd){
           this.x += toAdd.x;
           this.y += toAdd.y;

    }
}
