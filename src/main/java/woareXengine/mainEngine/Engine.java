package woareXengine.mainEngine;

import woareXengine.io.Timer;
import woareXengine.io.userInputs.Input;
import woareXengine.io.userInputs.Keyboard;
import woareXengine.io.userInputs.Mouse;
import woareXengine.io.window.Window;
import woareXengine.ui.main.Ui;
import woareXengine.util.Logger;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;

public class Engine {
    private static Engine currentInstance;

    private final EngineConfigs configs;

    private final Window window;

    private final Timer timer;
    private boolean closeFlag = false;

    public int defaultFboId;

    public boolean debugging = false;

    public Engine(EngineConfigs configs, Window window, Timer timer) {
        this.configs = configs;
        this.window = window;
        this.timer = timer;
        defaultFboId = glGenFramebuffers();

        GameSettings.viewportWidth = window.getPixelWidth();
        GameSettings.viewportHeight = window.getPixelHeight();
        GameSettings.displayMode = window.getDisplayMode();

        Logger.success("Engine initialized");
    }

    public static Engine instance() {
        if (currentInstance == null)
            throw new IllegalStateException("Engine not initialized yet.");
        return currentInstance;
    }

    public boolean isCloseRequested() {
        return closeFlag || window.closeButtonPressed();
    }

    public void update() {
        Ui.update();
        timer.update();
        Input.update();
        window.update();
        glClearColor(configs.backgroundColor.getR(), configs.backgroundColor.getG(), configs.backgroundColor.getB(), configs.backgroundColor.getA());
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void requestClose() {
        this.closeFlag = true;
    }

    public void closeEngine() {
        window.destroy();
    }

    /**
     * Initializes an instance of the Engine. A splash screen will be shown while the engine is setting up. When this
     * method returns the engine and its resources are guaranteed to be fully loaded. The splash screen will still be
     * showing, allowing the user to continue loading the game while the splash screen shows.
     *
     * @param configs The engine settings.
     * @return The engine.
     */
    public static Engine init(EngineConfigs configs) {
        if (currentInstance != null) {
            Logger.error("Engine has already been initialized!");
        } else {
            Logger.info("Initializing engine...");
            currentInstance = new EngineCreator().init(configs);
        }
        return currentInstance;
    }


    public static float getDelta() {
        return instance().timer.getDeltaTime() * instance().configs.gameSpeed;
    }

    public static void setGameSpeed(float speed) {
        instance().configs.gameSpeed = speed;
    }

    public static float getFps() {
        return 1 / (getDelta() / instance().configs.gameSpeed);
    }

    public static Window window() {
        return instance().window;
    }
}
