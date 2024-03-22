//package com.mygdx.game.Scenes;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.Input;
//
//
//public class EndMenu extends Scene {
//    public EndMenu(SceneManager sceneManager) {
//        super(sceneManager, "EndMenu.png", "This is the EndMenu Scene.");
//    }
//
//    @Override
//    public void initialize() {
//        // Initialize any necessary resources for the end menu
//    }
//
//    @Override
//    public void update(float deltaTime) {
//        // Update logic for the end menu
//    }
//
//    @Override
//    public void render() {
//        super.render();
//        batch.begin();
//        font.draw(batch,"This is the EndMenu Scene.", 1, 450);
//        font.draw(batch,"Press X to transit to LeaderBoard Scene.", 1, 400);
//        font.draw(batch,"Press SPACEBAR to transit to MainMenu Scene.", 1, 300);
//        batch.end();
//    }
//
//    @Override
//    public void handleInput() {
//        if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
//            sceneManager.pushScene(new Leaderboard(sceneManager));
//        }
//        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
//            sceneManager.pushScene(new MainMenu(sceneManager));
//        }
//    }
//
//    @Override
//    public void dispose() {
//        super.dispose();
//    }
//}
