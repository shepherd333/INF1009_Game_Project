package com.mygdx.game;

import java.util.List;

public class CollisionManager {

    public interface Collidable {
        float getX();
        float getY();
        float getWidth();
        float getHeight();
        boolean collidesWith(Collidable other);
    }

    public static class Entity implements Collidable {
        protected float x, y, width, height;

        public Entity(float x, float y, float width, float height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public float getX() { return x; }
        public float getY() { return y; }
        public float getWidth() { return width; }
        public float getHeight() { return height; }

        public boolean collidesWith(Collidable other) {
            return this.x < other.getX() + other.getWidth() &&
                    this.x + this.width > other.getX() &&
                    this.y < other.getY() + other.getHeight() &&
                    this.y + this.height > other.getY();
        }
    }

    public static class MovableEntity extends Entity {
        protected float dx, dy;

        public MovableEntity(float x, float y, float width, float height, float dx, float dy) {
            super(x, y, width, height);
            this.dx = dx;
            this.dy = dy;
        }

        public void update() {
            // Update position based on velocity
            this.x += this.dx;
            this.y += this.dy;
        }
    }

    public static class CollisionSystem {
        public void update(List<Collidable> entities) {
            for (int i = 0; i < entities.size(); i++) {
                Collidable entity1 = entities.get(i);

                if (entity1 instanceof MovableEntity) {
                    ((MovableEntity) entity1).update();
                }

                for (int j = i + 1; j < entities.size(); j++) {
                    Collidable entity2 = entities.get(j);

                    if (entity1.collidesWith(entity2)) {
                        // Collision detected between entity1 and entity2
                        System.out.println("Collision detected between entity1 and entity2");
                        // Handle collision...
                    }
                }
            }
        }
    }
}