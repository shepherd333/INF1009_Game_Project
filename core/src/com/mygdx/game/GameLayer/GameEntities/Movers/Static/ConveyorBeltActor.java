package com.mygdx.game.GameLayer.GameEntities.Movers.Static;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Array;

public class ConveyorBeltActor extends Actor {
    private Animation<TextureRegion> conveyorAnimation;
    private float stateTime;
    private TextureAtlas conveyorAtlas;

    public ConveyorBeltActor() {
        conveyorAtlas = new TextureAtlas(Gdx.files.internal("conveyor.atlas"));
        Array<TextureAtlas.AtlasRegion> frames = conveyorAtlas.findRegions("conveyor");
        conveyorAnimation = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
        stateTime = 0f;

        TextureRegion initialFrame = frames.first();
        setSize(initialFrame.getRegionWidth(), initialFrame.getRegionHeight()*2);
        setPosition(0, 0);
    }

    @Override
    public void act(float delta) {
        stateTime += delta;
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion currentFrame = conveyorAnimation.getKeyFrame(stateTime, true);
        float currentWidth = currentFrame.getRegionWidth();
        float screenWidth = Gdx.graphics.getWidth();
        int tileCount = (int) Math.ceil(screenWidth / currentWidth);

        // Tile the animation across the width of the screen
        for (int i = 0; i < tileCount; i++) {
            batch.draw(currentFrame, getX() + i * currentWidth, getY(), currentWidth, getHeight());
        }
    }

    public void dispose() {
        conveyorAtlas.dispose();
    }
}

