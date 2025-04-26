package TDA.ui.components.inventory.itemInventory;

import TDA.entities.inventory.items.ItemStack;
import woareXengine.io.userInputs.Input;
import woareXengine.ui.components.UiComponent;
import woareXengine.ui.constraints.PixelConstraint;
import woareXengine.ui.constraints.UiConstraints;
import woareXengine.ui.text.basics.Text;
import woareXengine.util.Assets;
import woareXengine.util.Color;

public class InventoryItemIcon extends UiComponent {
    private final ItemStack itemStack;
    private Text amountText = null;

    public InventoryItemIcon(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    protected void init() {
        color = Color.WHITE;
        texture = itemStack.item.getTexture();

        if (amountText == null && itemStack.item.isStackable()) {
            amountText = Assets.getDefaultFont().createText(String.valueOf(itemStack.amount), 0.5f);
            amountText.color = Color.WHITE;
            add(amountText, new UiConstraints(
                    new PixelConstraint(0, true),
                    new PixelConstraint(0),
                    new PixelConstraint((int) amountText.calculateWidth()),
                    new PixelConstraint((int) amountText.calculateHeight())
            ));
        }
    }

    @Override
    protected void updateSelf() {
        if (itemStack == null) return;

        if (!amountText.textString.equals(itemStack.amount + "")) {
            amountText.textString = itemStack.amount + "";
        }

        if (isMouseOver()) {
            if (Input.mouse().getScroll() > 0) {
                itemStack.amount++;
            }
            if (Input.mouse().getScroll() < 0) {
                itemStack.amount--;
            }
        }
    }

    public ItemStack getItem() {
        return itemStack;
    }
}
