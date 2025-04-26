package woareXengine.io.userInputs;

import woareXengine.io.window.Window;

public class Input {
    private static Keyboard keyboard;
    private static Mouse mouse;

    public static void init(Window window) {
        keyboard = new Keyboard(window);
        mouse = new Mouse(window);
    }

    public static void update() {
        keyboard.update();
        mouse.update();
    }

    public static Keyboard keyboard() {
        return keyboard;
    }

    public static Mouse mouse() {
        return mouse;
    }
}
