package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.AIManagement.AIManager;

public class RaindropEntity extends MovingEntities {
    public RaindropEntity(Texture texture, float x, float y, float speed) {
        super(texture.toString(), x, y, speed, true);
    }
    private AIManager aiManager;
    public void update(float deltaTime, float spawnTimer, float spawnInterval, Texture raindropTexture) {
        spawnTimer = deltaTime;
        if (spawnTimer >= spawnInterval) {
            this.aiManager=aiManager;
            aiManager.spawnRaindrop(raindropTexture);
        spawnTimer = 0;
        }
    }
}