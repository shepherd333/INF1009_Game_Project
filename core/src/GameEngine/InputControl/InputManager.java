package GameEngine.InputControl;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;


public class InputManager {
    private Stage stage;

    public InputManager(Stage stage) {
        this.stage = stage;
    }

    public boolean isSpacePressed() {
        return Gdx.input.isKeyJustPressed(Input.Keys.SPACE);
    }
}


