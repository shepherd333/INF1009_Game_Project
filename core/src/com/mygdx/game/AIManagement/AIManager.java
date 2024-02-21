package com.mygdx.game.AIManagement;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.EntityManagement.EntityManager;
import com.mygdx.game.EntityManagement.RaindropEntity;

import java.util.Random;

public class AIManager {
    private EntityManager entityManager;
    private Random random = new Random();
    private final int screenWidth;

    private Texture raindropTexture;
    private float raindropSpeed;
    public AIManager(EntityManager entityManager, int screenWidth) {
        this.entityManager = entityManager;
        this.screenWidth = screenWidth;
    }

    public void spawnRaindrop(Texture texture) {

        int x = random.nextInt(screenWidth);
        RaindropEntity raindrop = new RaindropEntity(raindropTexture,x,0,raindropSpeed);
        entityManager.addEntity(raindrop);
    }
}
