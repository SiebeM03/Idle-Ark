package woareXengine.ui.main;

import org.joml.Vector2f;
import woareXengine.io.userInputs.Keyboard;
import woareXengine.io.userInputs.Mouse;
import woareXengine.io.window.Window;
import woareXengine.mainEngine.Engine;
import woareXengine.rendering.uiRenderer.UiRenderer;
import woareXengine.ui.text.basics.Font;
import woareXengine.ui.text.rendering.FontRenderer;
import woareXengine.util.Transform;

public class Ui {
    private static final UiContainer CONTAINER = new UiContainer();

    private static UiRenderer uiRenderer;
    public static FontRenderer fontRenderer;

    public static int displayWidthPixels;
    public static int displayHeightPixels;

    private static boolean mouseInteractionEnabled = true;

    public static void init(Window window) {
        fontRenderer = new FontRenderer();
        Ui.uiRenderer = new UiRenderer();

        Ui.uiRenderer.add(CONTAINER);
        CONTAINER.init();

        Ui.displayWidthPixels = window.getPixelWidth();
        Ui.displayHeightPixels = window.getPixelHeight();
        window.addSizeChangeListener(Ui::notifyScreenSizeChange);
        notifyScreenSizeChange(window.getPixelWidth(), window.getPixelHeight());
    }

    public static void update() {
        CONTAINER.update(Engine.getDelta(), true);
        uiRenderer.render();
        for (Font font : Font.texts.keySet()) {
            fontRenderer.setActiveFont(font);
            fontRenderer.data = Font.texts.get(font);
            fontRenderer.render();
        }
    }

    public static void notifyScreenSizeChange(int width, int height) {
        displayWidthPixels = width;
        displayHeightPixels = height;
        CONTAINER.transform.setDimensions(width, height);
    }

    public static UiContainer getContainer() {
        return CONTAINER;
    }

    public static void enableMouseInteraction(boolean enable) {
        mouseInteractionEnabled = enable;
    }

    public static boolean isMouseEnabled() {
        return mouseInteractionEnabled;
    }
}
