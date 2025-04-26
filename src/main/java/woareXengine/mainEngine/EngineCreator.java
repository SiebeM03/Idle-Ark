package woareXengine.mainEngine;

import woareXengine.io.Timer;
import woareXengine.io.userInputs.Input;
import woareXengine.io.window.Window;
import woareXengine.ui.main.Ui;
import woareXengine.util.Logger;

import static org.lwjgl.opengl.GL.createCapabilities;

public class EngineCreator {

    private Window window;
    private Timer timer;

    protected Engine init(EngineConfigs configs) {
        initEssentialSystems(configs);
        initEngineSystems(configs);
        return new Engine(configs, window, timer);
    }

    /**
     * Init the systems that are needed to show the splash screen.
     *
     * @param configs Engine configs.
     */
    private void initEssentialSystems(EngineConfigs configs) {
        this.window = setUpWindow(configs);
        createCapabilities();
        this.timer = new Timer();
        Input.init(window);
        Ui.init(window);
    }

    /**
     * Init the rest of the engine systems. The splash screen will be showing while this is happening.
     *
     * @param configs Engine configs.
     */
    private void initEngineSystems(EngineConfigs configs) {

    }

    private Window setUpWindow(EngineConfigs configs) {
//        Buffer buffer = loadWindowIcon();
        Window window = Window.newWindow(configs.windowWidth, configs.windowHeight, configs.windowTitle)
                                .setVsync(configs.vsync)
                                .displayMode(configs.displayMode)
                                // .withIcon(buffer)
                                .setMSAA(configs.msaa)
                                .create();
        Logger.success("Window created");
        return window;
    }

}
