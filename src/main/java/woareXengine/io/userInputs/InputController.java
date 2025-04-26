package woareXengine.io.userInputs;

import woareXengine.util.Logger;

/**
 * A wrapper for {@link Mouse} and {@link Keyboard} that can enable both inputs independently
 */
public class InputController {
    protected final Mouse mouse;
    protected boolean isMouseEnabled = true;

    protected final Keyboard keyboard;
    protected boolean isKeyboardEnabled = true;

    public InputController() {
        this.mouse = Input.mouse();
        this.keyboard = Input.keyboard();

        if (mouse == null || keyboard == null) {
            Logger.error("InputController: Mouse or Keyboard is null");
        }
    }

    public void enableInput(boolean enable) {
        enableMouseUse(enable);
        enableKeyboardUse(enable);
    }

    public void enableMouseUse(boolean enable) {
        isMouseEnabled = enable;
    }

    public void enableKeyboardUse(boolean enable) {
        isKeyboardEnabled = enable;
    }


}
