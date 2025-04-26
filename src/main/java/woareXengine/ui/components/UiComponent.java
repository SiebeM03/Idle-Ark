package woareXengine.ui.components;

import org.joml.Vector2f;
import woareXengine.io.userInputs.Input;
import woareXengine.io.userInputs.Mouse;
import woareXengine.mainEngine.Engine;
import woareXengine.rendering.renderData.RenderObject;
import woareXengine.ui.constraints.UiConstraints;
import woareXengine.ui.main.Ui;
import woareXengine.ui.text.basics.Font;
import woareXengine.ui.text.basics.Text;
import woareXengine.util.Color;
import woareXengine.util.Logger;
import woareXengine.util.SafeList;
import woareXengine.util.Transform;

public abstract class UiComponent extends RenderObject {
    protected UiComponent parent;

    public final SafeList<UiComponent> children = new SafeList<>();

    public Transform transform = new Transform();
    public Color color = new Color(0, 0, 0, 0);

    /** Whether the individual component is shown */
    private boolean shown = true;
    /** Whether the component is visible, affected by all parents' shown state */
    private boolean visible = true;

    protected UiConstraints constraints;

    private int level = 0;

    public void add(UiComponent component, UiConstraints constraints) {
        component.parent = this;
        component.constraints = constraints;
        component.constraints.notifyAdded(component, component.parent);

        children.add(component);
        component.show(true);
        component.init();
    }

    public void remove(UiComponent component) {
        component.show(false);
        component.parent = null;
        children.remove(component);

        if (component instanceof Text) {
            Font.removeText((Text) component);
        }
    }

    public void show(boolean show) {
        if (shown == show) return;
        this.shown = show;
        changeVisibility(parent == null || parent.visible);
    }

    private void changeVisibility(boolean parentVisibility) {
        boolean newVisibility = shown && parentVisibility;
        if (newVisibility == visible) return;
        setVisibility(newVisibility);
        for (UiComponent child : children) {
            child.changeVisibility(visible);
        }
    }

    protected void setVisibility(boolean newVisibility) {
        this.visible = newVisibility;
    }

    /**
     * @return The shown state of this component and this component only, regardless of the shown state of parent
     * components. This can be true but the component is still hidden, due to parents being hidden. To test if this
     * component is at all visible, use the isVisible() method.
     */
    public boolean isShown() {
        return shown;
    }

    /**
     * @return true if this component will be rendered. If any parent components are hidden then this component will not
     * be visible. It however does not take into account whether this component is cut off by the scissor test or off
     * the limits of the screen (as it will still be sent to the renderer).
     */
    public boolean isVisible() {
        if (parent == null) {
            return visible;
        }
        return visible && parent.isVisible();
    }

    public final void update(float dt, boolean parentDisplayed) {
        if (!visible) return;
        children.applyChanges();

        updateSelf();

        for (UiComponent child : children) {
            child.update(dt, parentDisplayed);
        }
    }

    protected abstract void init();

    protected abstract void updateSelf();

    public Transform getAbsoluteTransform() {
        if (parent == null) {
            return transform;
        }
        return new Transform(
                transform.getPosition().add(parent.getAbsoluteTransform().getPosition(), new Vector2f()),
                transform.copy().getDimensions()
        );
    }

    public boolean isMouseOver() {
        if (!Ui.isMouseEnabled() || !isShown()) return false;
        Mouse mouse = Input.mouse();

        return getAbsoluteTransform().contains(mouse.getScreenX(), mouse.getScreenY());
    }

    public UiComponent getParent() {
        return parent;
    }

    public void setParent(UiComponent newParent) {
        parent.remove(this);
        newParent.add(this, constraints);
    }

    public UiConstraints getConstraints() {
        return constraints;
    }
}
