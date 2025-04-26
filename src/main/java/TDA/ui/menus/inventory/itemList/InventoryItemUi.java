package TDA.ui.menus.inventory.itemList;

import TDA.entities.inventory.items.ItemStack;
import woareXengine.io.userInputs.Input;
import woareXengine.mainEngine.Engine;
import woareXengine.ui.components.UiComponent;
import woareXengine.ui.constraints.PixelConstraint;
import woareXengine.ui.constraints.Position;
import woareXengine.ui.constraints.PositionConstraint;
import woareXengine.ui.constraints.UiConstraints;
import woareXengine.ui.text.basics.Text;
import woareXengine.util.Assets;
import woareXengine.util.Color;

public class InventoryItemUi extends UiComponent {
    public Text amountText = null;

    @Override
    protected void init() {
        if (getItemStack() != null) {
            texture = getItemStack().item.getTexture();
            color = Color.WHITE;

            if (amountText == null) {
                amountText = Assets.getDefaultFont().createText(getItemStack().amount + "", 0.5f);
                add(amountText, new UiConstraints(
                        new PositionConstraint(Position.RIGHT, 4),
                        new PositionConstraint(Position.BOTTOM, 4),
                        new PixelConstraint((int) amountText.calculateWidth()),
                        new PixelConstraint((int) amountText.calculateHeight())
                ));
                updateTextTransform();
            }
        }
    }

    @Override
    protected void updateSelf() {
        if (getItemStack() == null) return;

        if (!amountText.textString.equals(getItemStack().amount + "")) {
            amountText.textString = getItemStack().amount + "";
            updateTextTransform();
        }

        if (isMouseOver()) {
            if (Input.mouse().getScroll() > 0) {
                getItemStack().amount++;
            }
            if (Input.mouse().getScroll() < 0) {
                getItemStack().amount--;
            }
        }
    }

    public void updateTextTransform() {
        amountText.getConstraints().getWidth().setPixelValue((int) amountText.calculateWidth());
        amountText.getConstraints().getHeight().setPixelValue((int) amountText.calculateHeight());
        amountText.getConstraints().apply();
    }

    public ItemStack getItemStack() {
        if (parent instanceof InventorySlot) {
            return ((InventorySlot) parent).getItemStack();
        }
        return null;
    }
}
