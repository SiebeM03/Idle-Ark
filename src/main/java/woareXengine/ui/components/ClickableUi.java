package woareXengine.ui.components;

import woareXengine.io.userInputs.Input;
import woareXengine.io.userInputs.Mouse;
import woareXengine.io.userInputs.MouseButton;
import woareXengine.ui.main.Ui;

import java.util.ArrayList;
import java.util.List;

public abstract class ClickableUi extends UiComponent {
    private final List<MouseListener> listeners = new ArrayList<>();
    private boolean mousedOver = false;

    @Override
    protected void updateSelf() {
        checkMouseOver();
        if (mousedOver) {
            checkClicks();
        }
    }

    private void checkClicks() {
        Mouse mouse = Input.mouse();

        checkMouseButton(MouseButton.LEFT, mouse);
        checkMouseButton(MouseButton.MIDDLE, mouse);
        checkMouseButton(MouseButton.RIGHT, mouse);
    }

    private void checkMouseButton(MouseButton button, Mouse mouse) {
        if (mouse.isClickEvent(button)) {
            click(button, true);
        } else if (mouse.isReleaseEvent(button)) {
            click(button, false);
        }
    }

    protected void click(MouseButton button, boolean buttonDown) {
        fireListeners(new EventData(button, buttonDown));
    }

    private void checkMouseOver() {
        boolean mouseOverStatus = super.isMouseOver();
        if (mouseOverStatus != mousedOver) {
            mouseOver(mouseOverStatus);
        }
    }

    protected void mouseOver(boolean newMouseOverState) {
        mousedOver = newMouseOverState;
        fireListeners(new EventData(null, mousedOver));
    }


    public void addMouseListener(MouseListener listener) {
        listeners.add(listener);
    }

    private void fireListeners(EventData data) {
        int pointer = 0;
        while (pointer < listeners.size()) {
            listeners.get(pointer).eventOccurred(data);
            pointer++;
        }
    }
}
