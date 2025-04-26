package woareXengine.mainEngine;

import woareXengine.io.window.DisplayMode;
import woareXengine.util.Color;

public class EngineConfigs {

    public int windowWidth = 1280;
    public int windowHeight = 720;

    public Color backgroundColor = new Color(Color.BLACK);

    public DisplayMode displayMode = DisplayMode.FULLSCREEN;
    public String windowTitle;
    public boolean vsync = true;
    public boolean msaa = true;

    public float gameSpeed = 1.0f;


    public static EngineConfigs getDefaultConfigs() {
        return new EngineConfigs();
    }
}
