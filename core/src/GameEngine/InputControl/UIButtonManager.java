package GameEngine.InputControl;
import GameEngine.Collisions.handlers.enums.ItemType;
import GameEngine.SimulationLifecycleManagement.AudioManager;
import GameEngine.SimulationLifecycleManagement.LevelConfig;
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
    private TextButton muteButton; // Add this field
    private ButtonConfigurator buttonConfigurator;

    public UIButtonManager(Skin skin, Stage stage, SceneManager sceneManager) {
        this.skin = skin;
        this.stage = stage;
        this.sceneManager = sceneManager; // Initialize SceneManager
    }
    public void setButtonConfigurator(ButtonConfigurator buttonConfigurator) {
        this.buttonConfigurator = buttonConfigurator;
    }

    public void configureButtons() {
        if (this.buttonConfigurator != null) {
            this.buttonConfigurator.configureButtons(this);
        }
    }

    public TextButton createOrUpdateMuteButton(int x, int y) {
        AudioManager audioManager = AudioManager.getInstance();
        if (muteButton == null) {
            muteButton = new TextButton("", skin); // Initially, the button text is empty
            muteButton.setSize(buttonWidth, buttonHeight);
            muteButton.setPosition(x, y);
            muteButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    audioManager.toggleMusicMute();
                    // Update text based on current state right after toggling
                    muteButton.setText(audioManager.isMusicMuted() ? "Unmute" : "Mute");
                }
            });
            stage.addActor(muteButton);
        }
        // Always update button text based on current mute state
        muteButton.setText(audioManager.isMusicMuted() ? "Unmute" : "Mute");
        return muteButton;
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
                sceneManager.set(new GameObjective(sceneManager));
            }
        }, x, startY - (buttonHeight + spacing), buttonWidth, buttonHeight);

        // Mute Button
        createOrUpdateMuteButton(x, startY - 2 * (buttonHeight + spacing));

        // Exit Game Button
        addButton("Exit Game", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        }, x, startY - 3 * (buttonHeight + spacing), buttonWidth, buttonHeight);
    }

    public void setupPauseMenu() {
        int startY = calculateStartY() - buttonHeight; // Adjust startY as needed based on your UI design
        int x = (Gdx.graphics.getWidth() - buttonWidth) / 2; // Centered horizontally
        AudioManager.getInstance().stopCountdownSound();

        // Resume Button
        addButton("Resume", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneManager.popScene(); // Return to the gameplay scene
                AudioManager.getInstance().playSoundEffect("countdown", 1.0f);
            }
        }, x, startY, buttonWidth, buttonHeight);

        // How to Play Button
        addButton("How to Play", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Assuming PauseMenuHowToPlay is correctly implemented to be launched from here
                sceneManager.set(new PauseMenuHowToPlay(sceneManager));
            }
        }, x, startY - (buttonHeight + spacing), buttonWidth, buttonHeight);

        // Mute Button
        createOrUpdateMuteButton(x, startY - 2 * (buttonHeight + spacing));
        // Exit to Home Button
        addButton("Exit to Home", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Transition to the main menu
                sceneManager.set(new MainMenu(sceneManager));
                AudioManager.getInstance().stopCountdownSound();
            }
        }, x, startY - 3 * (buttonHeight + spacing), buttonWidth, buttonHeight);
    }

    public void setupLevelMenu() {
        int startY = calculateStartY() - buttonHeight; // Adjust as necessary
        int x = (Gdx.graphics.getWidth() - buttonWidth) / 2;

        // Define level selection buttons
        addButton("Level 1", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                LevelConfig level1Config = new LevelConfig(0.5f, 1f, 0, new ItemType[]{ItemType.GLASS, ItemType.PAPER, ItemType.PLASTIC}, 1);
                sceneManager.set(new GamePlay(sceneManager, level1Config));
            }
        }, x, startY, buttonWidth, buttonHeight);

        addButton("Level 2", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                LevelConfig level2Config = new LevelConfig(1f, 1.5f, 2, new ItemType[]{ItemType.GLASS, ItemType.PAPER, ItemType.PLASTIC, ItemType.METAL}, 2);
                sceneManager.set(new GamePlay(sceneManager, level2Config));
            }
        }, x, startY - (buttonHeight + spacing), buttonWidth, buttonHeight);

        addButton("Level 3", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                LevelConfig level3Config = new LevelConfig(1.5f, 2f, 3, new ItemType[]{ItemType.GLASS, ItemType.PAPER, ItemType.PLASTIC, ItemType.METAL, ItemType.TRASH}, 3);
                sceneManager.set(new GamePlay(sceneManager, level3Config));
            }
        }, x, startY - 2 * (buttonHeight + spacing), buttonWidth, buttonHeight);

        // Back Button
        addButton("Back", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneManager.set(new MainMenu(sceneManager));
            }
        }, x, startY - 3 * (buttonHeight + spacing), buttonWidth, buttonHeight);
    }

    public void setupGamePlay() {
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        int rightMargin = 10;
        int topMargin = 10;
        int buttonWidth = 100;
        int buttonHeight = 25;

        // Play Game Button
        addCustomButton("Pause", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneManager.pushScene(new PauseMenu(sceneManager));
            }
        }, screenWidth - buttonWidth - rightMargin, screenHeight - buttonHeight - topMargin, buttonWidth, buttonHeight);

        // Back to Home Button
        addCustomButton("Back to Home", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneManager.set(new MainMenu(sceneManager));
                AudioManager.getInstance().stopCountdownSound();
            }
        }, screenWidth - buttonWidth - rightMargin, screenHeight - 2*buttonHeight - topMargin - spacing, buttonWidth, buttonHeight);
    }

    public void setupEndMenu() {
        int buttonWidth = 200;
        int buttonHeight = 50;
        int screenHeight = Gdx.graphics.getHeight();
        int screenWidth = Gdx.graphics.getWidth();
        int buttonY = 50; // Custom Y position for the button

        // Since EndMenu only has one button in your example, we directly use its specific position
        addCustomButton("Back to Home", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneManager.set(new MainMenu(sceneManager));
            }
        }, (screenWidth - buttonWidth) / 2, buttonY, buttonWidth, buttonHeight);
    }
    public void setupHowToPlay() {
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        int rightMargin = 10;
        int topMargin = 10;
        int buttonWidth = 100;
        int buttonHeight = 25;

        // Play Game Button
        addCustomButton("Play Game", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneManager.set(new LevelMenu(sceneManager));
            }
        }, screenWidth - buttonWidth - rightMargin, screenHeight - buttonHeight - topMargin, buttonWidth, buttonHeight);

        // Back to Home Button
        addCustomButton("Back to Home", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneManager.set(new MainMenu(sceneManager));
            }
        }, screenWidth - buttonWidth - rightMargin, screenHeight - 2*buttonHeight - topMargin - spacing, buttonWidth, buttonHeight);

        // Back Button
        addCustomButton("Back", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneManager.set(new GameObjective(sceneManager));
            }
        }, screenWidth - buttonWidth - rightMargin, screenHeight - 4*buttonHeight - topMargin - 3*spacing, buttonWidth, buttonHeight);
    }

    private void addButton(String text, ClickListener listener, int x, int y, int width, int height) {
        TextButton button = new TextButton(text, skin);
        button.setSize(width, height);
        button.setPosition(x, y);
        button.addListener(listener);
        stage.addActor(button);
    }

    public void setupGameObjective() {
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        int buttonWidth = 100; // Smaller width for this specific scene
        int buttonHeight = 25;
        int rightMargin = 10;
        int topMargin = 10;

        // Next Button
        addCustomButton("Next", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneManager.set(new HowToPlay(sceneManager));
            }
        }, screenWidth - buttonWidth - rightMargin, screenHeight - buttonHeight - topMargin, buttonWidth, buttonHeight);
    }

    public void setupLeaderboard() {
        int buttonWidth = 100; // Adjusted to fit the scene's layout
        int buttonHeight = 25;
        // Coordinates specifically for the "Next" button in the Leaderboard scene
        int nextButtonX = 1085; // Example X coordinate, adjust based on actual UI design
        int nextButtonY = 745;  // Example Y coordinate, adjust based on actual UI design

        // Adding the "Next" button which transitions to the EndMenu
        addCustomButton("Next", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneManager.set(new EndMenu(sceneManager));
            }
        }, nextButtonX, nextButtonY, buttonWidth, buttonHeight);
    }

    public void setupPauseMenuHowToPlay() {
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        int buttonWidth = 100;
        int buttonHeight = 25;
        int rightMargin = 10;
        int topMargin = 10;

        // "Back" button that navigates back to the PauseMenu
        addCustomButton("Back", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneManager.set(new PauseMenu(sceneManager));
            }
        }, screenWidth - buttonWidth - rightMargin, screenHeight - buttonHeight - topMargin, buttonWidth, buttonHeight);
    }

    private int calculateStartY() {
        // Simplified for demonstration
        return (Gdx.graphics.getHeight() / 2) + ((buttonHeight + spacing) * 2);
    }

    public void addCustomButton(String text, ClickListener listener, int x, int y, int width, int height) {
        TextButton button = new TextButton(text, skin);
        button.setSize(width, height);
        button.setPosition(x, y);
        button.addListener(listener);
        stage.addActor(button);
    }
}
