package com.tfg.UIandroidGameEngine;

public abstract class Asset {

    protected String name;

    private int resource;

    public Asset(int assetResource,String name){
        resource = assetResource;
        this.name = name;
    }

    public abstract void appendResourceToGameObject(BasicGameObject toGetAppendedResource);


}
