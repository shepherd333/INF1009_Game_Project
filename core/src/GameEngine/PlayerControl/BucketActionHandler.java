// BucketActionHandler.java
package GameEngine.PlayerControl;

/**
 * This class, BucketActionHandler, is responsible for handling the action related to the space bar press.
 * It uses an InputHandlerInterface to check if the space bar is currently being pressed.
 * The result of this check can be retrieved using the isSpaceBarPressed() method.
 */
public class BucketActionHandler {
    private final InputHandlerInterface inputHandler;

    public BucketActionHandler(InputHandlerInterface inputHandler) {
        this.inputHandler = inputHandler;
    }

    public boolean isSpaceBarPressed() {
        return inputHandler.isSpaceBarPressed();
    }
}