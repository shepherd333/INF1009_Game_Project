package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.graphics.Texture;

public class BucketEntity extends MovingEntities  {

    public BucketEntity(Texture texture, float x, float y, float speed) {
            super(texture.toString(), x, y, speed, false); // Bucket is not AI-controlled
        }

        @Override
        public void update() {
            // Update bucket logic
            // You can add specific bucket movement logic here if needed
        }

    }