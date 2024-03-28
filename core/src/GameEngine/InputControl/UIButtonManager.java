package GameEngine.InputControl;
import GameEngine.SimulationLifecycleManagement.AudioManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import GameEngine.SceneManagement.SceneManager;
import com.mygdx.game.GameLayer.Scenes.*;

public class UIButtonManager {
    private Skin skin;
    private Stage stage;
    private SceneManager sceneManager; // Add reference to SceneManager
    private final int buttonWidth = 200; // Example, adjust as needed
    private final int buttonHeight = 50; // Example, adjust as needed
    private final int spacing = 10; // Space between buttons

    public UIButtonManager(Skin skin, Stage stage, SceneManager sceneManager) {
        this.skin = skin;
        this.stage = stage;
        this.sceneManager = sceneManager; // Initialize SceneManager
    }

    // Method to setup MainMenu buttons with actions
    public void setupMainMenu() {
        int startY = calculateStartY();
        int x = (Gdx.graphics.getWidth() - buttonWidth) / 2;

        // Play Button
        addButton("Play", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneManager.set(new LevelMenu(sceneManager));
            }
        }, x, startY, buttonWidth, buttonHeight);

        // How to Play Button
        addButton("How to Play", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneManager.set(new HowToPlay(sceneManager));
            }
        }, x, startY - (buttonHeight + spacing), buttonWidth, buttonHeight);

        // Mute Button
        addButton("Mute", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AudioManager audioManager = AudioManager.getInstance();
                audioManager.toggleMusicMute();
                TextButton thisButton = (TextButton) event.getListenerActor();
                thisButton.setText(audioManager.isMusicMuted() ? "Unmute" : "Mute");
            }
        }, x, startY - 2 * (buttonHeight + spacing), buttonWidth, buttonHeight);

        // Exit Game Button
        addButton("Exit Game", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        }, x, startY - 3 * (buttonHeight + spacing), buttonWidth, buttonHeight);
    }

    private void addButton(String text, ClickListener listener, int x, int y, int width, int height) {
        TextButton button = new TextButton(text, skin);
        button.setSize(width, height);
        button.setPosition(x, y);
        button.addListener(listener);
        stage.addActor(button);
    }

    private int calculateStartY() {
        // Simplified for demonstration
        return (Gdx.graphics.getHeight() / 2) + ((buttonHeight + spacing) * 2);
    }
}
