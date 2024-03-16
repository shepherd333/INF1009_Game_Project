//package com.mygdx.game.Lifecycle.HighScore;
//
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.g2d.BitmapFont;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//
//public class ScoreRenderer {
//    private BitmapFont font;
//    private SpriteBatch batch;
//
//    public ScoreRenderer() {
//        batch = new SpriteBatch();
//        font = new BitmapFont();
//        font.setColor(Color.WHITE);
//        font.getData().setScale(1);
//    }
//
//    public void render(String score, SpriteBatch batch, float x, float y) {
//        batch.begin();
//        font.draw(batch, score, x, y);
//        batch.end();
//    }
//
//    public void dispose() {
//        if (batch != null) batch.dispose();
//        if (font != null) font.dispose();
//    }
//}
