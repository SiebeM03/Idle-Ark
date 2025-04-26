package woareXengine.ui.common;

import woareXengine.ui.components.UiBlock;
import woareXengine.ui.components.UiComponent;
import woareXengine.ui.constraints.ConstraintUtils;
import woareXengine.ui.constraints.RelativeConstraint;
import woareXengine.util.Color;

public class ProgressBar extends UiComponent {
    public float progress;
    public Color backgroundColor;
    public Color progressColor;

    private UiBlock backgroundBlock;
    private UiBlock progressBlock;

    @Override
    protected void init() {
        this.backgroundBlock = new UiBlock(backgroundColor);
        add(backgroundBlock, ConstraintUtils.fill());

        this.progressBlock = new UiBlock(progressColor);
        backgroundBlock.add(progressBlock, ConstraintUtils.fill().setWidth(new RelativeConstraint(progress)));
    }

    @Override
    protected void updateSelf() {

    }

    public void setProgress(float progress) {
        if (progress < 0) progress = 0;
        if (progress > 1) progress = 1;

        this.progress = progress;
        progressBlock.getConstraints().setWidth(new RelativeConstraint(progress));
        progressBlock.getConstraints().notifyAdded(progressBlock, backgroundBlock);
    }
}
