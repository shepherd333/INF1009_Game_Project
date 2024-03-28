// InputHandlerInterface.java
package GameEngine.PlayerControl;

/**
 * This interface defines a contract for input handling.
 * Any class that implements this interface will provide a mechanism to check if a specific key is currently being pressed.
 */
public interface InputHandlerInterface {
    boolean isKeyPressed(int key);
}