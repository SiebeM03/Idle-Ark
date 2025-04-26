package TDA.entities.player;

import TDA.entities.components.interactions.ColliderComp;
import TDA.entities.dinoInventory.DinoInventoryComp;
import TDA.entities.main.Entity;
import TDA.entities.components.rendering.QuadComp;
import TDA.entities.inventory.InventoryComp;
import TDA.entities.player.movement.CameraFollowComp;
import TDA.entities.player.movement.CharacterControllerComp;
import TDA.main.GameManager;
import org.joml.Vector2f;
import woareXengine.mainEngine.gameObjects.Camera;
import woareXengine.util.Assets;
import woareXengine.util.Layer;
import woareXengine.util.Logger;
import woareXengine.util.Transform;

public class PlayerPrefab {
    private static Entity player = null;
    private static HotbarComp hotbar = new HotbarComp();
    private static InventoryComp inventory = new InventoryComp(30);
    private static DinoInventoryComp dinoInventory = new DinoInventoryComp(10);

    public static Entity getPlayer() {
        if (player == null) {
            player = createPlayer(600, 500);
        }
        return player;
    }

    public static Entity createPlayer(float x, float y) {
        if (GameManager.currentScene == null) {
            Logger.error("No scene to create player in!");
            return null;
        }
        return createPlayer(x, y, GameManager.currentScene.camera);
    }

    public static Entity createPlayer(float x, float y, Camera camera) {
        if (player != null) {
            Logger.error("Player already exists!");
            return player;
        }

        player = new Entity(new Transform(new Vector2f(x, y), new Vector2f(200, 200)))
                         .addComponent(new QuadComp(Assets.getTexture("src/assets/images/character.png"), Layer.FOREGROUND))
                         .addComponent(new ColliderComp((float) 350 / 600, (float) 110 / 600, (float) 220 / 600, (float) 230 / 600))
                         .addComponent(inventory)
                         .addComponent(hotbar)
                         .addComponent(new CharacterControllerComp())
                         .addComponent(new CameraFollowComp(camera))
                         .addComponent(new PlayerActionsComp());

        return player;
    }

    public static InventoryComp getInventory() {
        return inventory;
    }

    public static HotbarComp getHotbar() {
        return hotbar;
    }
}
