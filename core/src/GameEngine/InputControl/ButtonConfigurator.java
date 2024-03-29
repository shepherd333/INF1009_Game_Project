package GameEngine.InputControl;

import com.mygdx.game.GameLayer.Scenes.UIButtonManager;

/**
 * ButtonConfigurator is an interface that provides a contract for classes that configure buttons in a user interface.
 * This includes:
 * - Defining a method to configure buttons using the specified UIButtonManager.
 */
public interface ButtonConfigurator {
    // Method to configure buttons using the specified UIButtonManager.
    void configureButtons(UIButtonManager manager);
}
