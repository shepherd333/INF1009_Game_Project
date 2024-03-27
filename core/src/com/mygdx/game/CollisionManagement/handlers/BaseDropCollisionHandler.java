package com.mygdx.game.CollisionManagement.handlers;

import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class BaseDropCollisionHandler implements IDropCollisionHandler {
    protected Actor actor1;
    protected Actor actor2;


    public BaseDropCollisionHandler(Actor actor1, Actor actor2){
        this.actor1 = actor1;
        this.actor2 = actor2;

    }

    @Override
    public abstract void handleDropCollision();

}