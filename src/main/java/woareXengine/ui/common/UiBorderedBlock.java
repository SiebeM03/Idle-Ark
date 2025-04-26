package woareXengine.ui.common;

import woareXengine.ui.components.UiBlock;
import woareXengine.ui.constraints.ConstraintUtils;
import woareXengine.util.Color;
import woareXengine.util.Transform;

public class UiBorderedBlock extends UiBlock {
    private Color BORDER_COLOR = new Color(1, 1, 1, 0);
    private Color FILL_COLOR = new Color(1, 1, 1, 0);
    private int BORDER_WIDTH = 2;

    private UiBlock contentBlock;

    @Override
    protected void init() {
        color = BORDER_COLOR;

        contentBlock = new UiBlock();
        contentBlock.color = FILL_COLOR;
        add(contentBlock, ConstraintUtils.margin(BORDER_WIDTH));
    }

    public void setBorderWidth(int width) {
        contentBlock.getConstraints().getX().setPixelValue(width);
        contentBlock.getConstraints().getY().setPixelValue(width);
        contentBlock.getConstraints().getWidth().setPixelValue(width * 2);
        contentBlock.getConstraints().getHeight().setPixelValue(width * 2);
        contentBlock.getConstraints().apply();
    }

    public void setBorderColor(Color color) {
        BORDER_COLOR = color;
        this.color = color;
    }

    public void setFillColor(Color color) {
        FILL_COLOR = color;
        contentBlock.color = color;
    }
}
