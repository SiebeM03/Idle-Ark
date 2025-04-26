package TDA.scene.prefabs.homeScene;

import TDA.entities.dinos.types.giga.Giga;
import TDA.entities.dinos.types.rex.Rex;
import TDA.entities.flag.Flag;
import TDA.entities.inventory.items.ItemStack;
import TDA.entities.player.PlayerPrefab;
import TDA.entities.resources.items.types.StoneItem;
import TDA.entities.resources.items.types.TreeItem;
import TDA.entities.resources.nodes.types.Metal;
import TDA.entities.resources.nodes.types.Stone;
import TDA.entities.resources.nodes.types.Tree;
import TDA.entities.storage.entities.BarrelStorage;
import TDA.main.world.World;
import TDA.rendering.TDARenderEngine.renderSystem.TDARenderSystem;
import TDA.scene.Scene;
import TDA.scene.systems.InventoryManager;
import TDA.scene.systems.InventoryManagerOld;
import woareXengine.util.Logger;

public class HomeScene extends Scene {

    public World world;

    public HomeScene() {
        super(new TDARenderSystem(), new HomeCamera());
        Logger.success("Home Scene initialized");
    }

    @Override
    protected void createEntities() {
        addEntity(PlayerPrefab.getPlayer());
        PlayerPrefab.getInventory().addItem(new ItemStack(new StoneItem(), 30));
        PlayerPrefab.getInventory().addItem(new ItemStack(new TreeItem(), 10));

        addEntity(Tree.create(1300, 300));
        addEntity(Stone.create(1000, 300));
        addEntity(Metal.create(1600, 300));

        addEntity(BarrelStorage.create(1000, 100));

        addEntity(Flag.create(200, 200, new Rex().withStats(100, 100, 100), null, null, null, null));
        addEntity(Flag.create(800, 100, new Rex().withStats(30, 20, 10), new Giga().withStats(400, 20, 30), null, null, null));

        world = new World();
        world.init();

        Logger.success("Created entities and world for Home Scene");
    }

    @Override
    protected void addSystems() {
        addSystem(new InventoryManagerOld());
        addSystem(new InventoryManager());

        Logger.success("Created systems for Home Scene");
    }
}
