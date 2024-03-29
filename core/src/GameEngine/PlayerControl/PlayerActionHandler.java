// PlayerActionHandler.java
package GameEngine.PlayerControl;

/**
 * This class, PlayerActionHandler, is responsible for handling the action related to the space bar press.
 * It uses an InputHandlerInterface to check if the space bar is currently being pressed.
 * The result of this check can be retrieved using the isSpaceBarPressed() method.
 */
public class PlayerActionHandler {
    private final InputHandlerInterface inputHandler;

    public PlayerActionHandler(InputHandlerInterface inputHandler) {
        this.inputHandler = inputHandler;
    }

    public boolean isSpaceBarPressed() {
        return inputHandler.isSpaceBarPressed();
    }
}