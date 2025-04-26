package woareXengine.io.userInputs;

import woareXengine.io.window.Window;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.*;

public class Keyboard {
    /** The set of keys that have been pressed this frame. */
    private final Set<Integer> keysPressedThisFrame = new HashSet<>();
    /** The set of keys that have been repeated this frame (being held). */
    private final Set<Integer> keysRepeatedThisFrame = new HashSet<>();
    /** The set of keys that have been released this frame. */
    private final Set<Integer> keysReleasedThisFrame = new HashSet<>();
    /** The set of keys that are currently down. */
    private final Set<Integer> keysDown = new HashSet<>();
    /** The set of characters that have been typed this frame. */
    private final List<Integer> charsThisFrame = new ArrayList<>();

    public Keyboard(Window window) {
        addKeyListener(window.getId());
        addTextListener(window.getId());
    }

    public void update() {
        keysPressedThisFrame.clear();
        keysReleasedThisFrame.clear();
        keysRepeatedThisFrame.clear();
        charsThisFrame.clear();
    }

    public boolean isKeyDown(int keyCode) {
        return keysDown.contains(keyCode);
    }

    public List<Integer> getChars() {
        return charsThisFrame;
    }

    public boolean keyPressEvent(int keyCode) {
        return keysPressedThisFrame.contains(keyCode);
    }

    public boolean keyPressEvent(int keyCode, boolean checkRepeats) {
        return keysPressedThisFrame.contains(keyCode) || (checkRepeats && keysRepeatedThisFrame.contains(keyCode));
    }

    public boolean keyReleaseEvent(int keyCode) {
        return keysReleasedThisFrame.contains(keyCode);
    }

    public boolean isAnyKeyPressed() {
        return !keysPressedThisFrame.isEmpty();
    }

    private void addTextListener(long windowId) {
        glfwSetCharCallback(windowId, (window, unicode) -> {
            charsThisFrame.add(unicode);
        });
    }

    private void addKeyListener(long windowId) {
        glfwSetKeyCallback(windowId, (window, key, scancode, action, mods) -> {
            if (action == GLFW_PRESS) {
                keysDown.add(key);
                keysPressedThisFrame.add(key);
            } else if (action == GLFW_RELEASE) {
                keysDown.remove((key));
                keysReleasedThisFrame.add(key);
            } else if (action == GLFW_REPEAT) {
                keysRepeatedThisFrame.add(key);
            }
        });
    }

}
