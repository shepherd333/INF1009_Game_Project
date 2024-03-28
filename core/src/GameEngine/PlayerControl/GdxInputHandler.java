// GdxInputHandler.java
package GameEngine.PlayerControl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * This class is an implementation of the InputHandlerInterface.
 * It uses the Gdx library to check if a specific key is currently being pressed.
 */
public class GdxInputHandler implements InputHandlerInterface {
    @Override
    public boolean isKeyPressed(int key) {
        return Gdx.input.isKeyPressed(key);
    }

    @Override
    public boolean isSpaceBarPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.SPACE);
    }
}