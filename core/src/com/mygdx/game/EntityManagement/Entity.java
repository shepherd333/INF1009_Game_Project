package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.graphics.Texture;

public abstract class Entity {
    private float x;
    private float y;
    private float speed;

    protected Texture texture;

    public Entity(float x, float y, float speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public Entity() {

    }

    public Texture getTexture() {
        return texture;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public abstract void update();

    public abstract void movement();

}