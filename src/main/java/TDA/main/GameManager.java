package TDA.main;

import TDA.entities.dinos.types.giga.Giga;
import TDA.entities.dinos.types.rex.Rex;
import TDA.entities.dinos.types.triceratops.Triceratops;
import TDA.entities.dinos.types.velociraptor.Velociraptor;
import TDA.main.configs.GameConfigs;
import TDA.main.controls.GameControls;
import TDA.main.world.World;
import TDA.scene.Scene;
import TDA.scene.prefabs.battleScene.BattleScene;
import TDA.scene.prefabs.homeScene.HomeScene;
import TDA.scene.systems.battle.BattleContext;
import TDA.scene.systems.battle.Team.AlliedTeam;
import TDA.scene.systems.battle.Team.EnemyTeam;
import TDA.ui.TDAUi;
import woareXengine.io.userInputs.Input;
import woareXengine.io.window.DisplayMode;
import woareXengine.mainEngine.Engine;
import woareXengine.mainEngine.GameSettings;
import woareXengine.util.Logger;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_O;

public class GameManager {
    private static Engine engine;
    private static GameConfigs configs;

    public static GameControls gameControls;

    public static HomeScene homeScene;
    public static Scene currentScene;

    public static World world;

    public static void init(GameConfigs gameConfigs) {
        Logger.info("Initializing Game manager");
        configs = gameConfigs;
        engine = Engine.init(gameConfigs);

        gameControls = new GameControls();

        homeScene = new HomeScene();
        currentScene = homeScene;
        currentScene.fill();
    }

    public static void update() {
        if (Input.keyboard().keyPressEvent(GLFW_KEY_O)) {
            if (currentScene == homeScene) {
                BattleContext battleContext = new BattleContext(
                        new AlliedTeam(
                                new Rex().withStats(100, 20, 160),
                                new Giga().withStats(100, 20, 160),
                                new Giga().withStats(100, 20, 160),
                                new Triceratops().withStats(100, 20, 160),
                                new Giga().withStats(100, 20, 160)),
                        new EnemyTeam(
                                new Velociraptor().withStats(89, 24, 20),
                                new Velociraptor().withStats(89, 24, 23),
                                new Velociraptor().withStats(89, 24, 290),
                                new Velociraptor().withStats(89, 24, 27),
                                new Velociraptor().withStats(89, 24, 29))
                );
                startBattle(battleContext);
            } else {
                switchScene(homeScene);
            }
        }


        if (gameControls.windowControls.isDisplayModeSwitchPressed()) {
            switch (GameSettings.getDisplayMode()) {
                case FULLSCREEN -> GameSettings.setDisplayMode(DisplayMode.WINDOWED);
                case WINDOWED -> GameSettings.setDisplayMode(DisplayMode.FULLSCREEN);
            }
        }

        if (gameControls.developerControls.isDevModeToggled()) {
            Engine.instance().debugging = !Engine.instance().debugging;
            Logger.debug("Toggled debugging: " + Engine.instance().debugging);
        }

        TDAUi.get().gameUi.update();
        currentScene.update();
        currentScene.render();

        engine.update();
    }

    public static boolean readyToClose() {
        return engine.isCloseRequested();
    }

    public static void switchScene(Scene scene) {
        currentScene.close();
        currentScene = scene;
    }

    public static void startBattle(BattleContext context) {
        switchScene(new BattleScene(context));
        currentScene.fill();
    }

    public static void cleanUp() {
        currentScene.cleanUp();
        engine.closeEngine();
    }
}
