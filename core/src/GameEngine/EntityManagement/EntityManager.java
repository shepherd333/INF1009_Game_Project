package GameEngine.EntityManagement;

import GameEngine.SimulationLifecycleManagement.LevelConfig;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GameLayer.GameEntities.Movers.BucketActor;
import com.mygdx.game.GameLayer.GameEntities.Movers.ItemActor;
import com.mygdx.game.GameLayer.GameEntities.Movers.ToxicWasteActor;
import com.mygdx.game.GameLayer.GameEntities.Static.BinActor;
import com.mygdx.game.GameLayer.GameEntities.Static.ConveyorBeltActor;
import com.mygdx.game.GameLayer.Scenes.GamePlay;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {
    private LevelConfig levelConfig;
    private final GamePlay gamePlay;
    private Stage stage;
    private ConveyorBeltActor conveyorBelt;
    private BucketActor bucket;
    private Texture bucketTexture;
    private List<CollidableActor> actors = new ArrayList<>();

    public EntityManager(GamePlay gamePlay, LevelConfig levelConfig) {
        this.gamePlay = gamePlay;
        this.levelConfig = levelConfig;
    }

    public void initializeGameEntities() {
        spawnToxicWaste(levelConfig.spawnToxicWaste);
        setupConveyorBelt();
    }



    private void spawnToxicWaste(int spawnToxicWaste) {
        for (int i = 0; i < spawnToxicWaste; i++) {
            ToxicWasteActor toxicwaste = new ToxicWasteActor();
            gamePlay.getStage().addActor(toxicwaste);
        }
    }

    private void setupConveyorBelt() {
        conveyorBelt = new ConveyorBeltActor();
        gamePlay.getStage().addActor(conveyorBelt);
    }
}
