package TDA.main.controls;

import TDA.ui.TDAUi;
import woareXengine.io.userInputs.InputController;
import woareXengine.io.userInputs.MouseButton;

import static org.lwjgl.glfw.GLFW.*;

public class InventoryControls extends InputController {
    protected InventoryControls() {
        super();
    }

    public int isHotbarItemSelected() {
        if (!isKeyboardEnabled) return -1;

        if (keyboard.keyPressEvent(GLFW_KEY_1)) return 0;
        if (keyboard.keyPressEvent(GLFW_KEY_2)) return 1;
        if (keyboard.keyPressEvent(GLFW_KEY_3)) return 2;
        if (keyboard.keyPressEvent(GLFW_KEY_4)) return 3;
        if (keyboard.keyPressEvent(GLFW_KEY_5)) return 4;
        if (keyboard.keyPressEvent(GLFW_KEY_6)) return 5;
        if (keyboard.keyPressEvent(GLFW_KEY_7)) return 6;
        if (keyboard.keyPressEvent(GLFW_KEY_8)) return 7;
        if (keyboard.keyPressEvent(GLFW_KEY_9)) return 8;
        if (keyboard.keyPressEvent(GLFW_KEY_0)) return 9;

        return -1;
    }

    public boolean shouldToggleInventory() {
        return isKeyboardEnabled && keyboard.keyPressEvent(GLFW_KEY_J);
    }

    public boolean shouldCloseInventory() {
        return isKeyboardEnabled && keyboard.keyPressEvent(GLFW_KEY_ESCAPE);
    }

    public boolean isClicked(MouseButton button) {
        return isMouseEnabled && mouse.isClickEvent(button);
    }


}
