package TDA.ui.components.inventory.itemInventory;

import TDA.entities.inventory.InventoryComp;
import TDA.entities.inventory.items.ItemStack;
import TDA.main.GameManager;
import TDA.scene.systems.InventoryManager;
import woareXengine.io.userInputs.MouseButton;
import woareXengine.ui.components.UiBlock;
import woareXengine.ui.components.UiComponent;
import woareXengine.ui.constraints.ConstraintUtils;
import woareXengine.util.Assets;
import woareXengine.util.Color;

public class InventorySlot extends UiComponent {
    public static final int PIXEL_SIZE = 10;
    public static final int SCALE = 5;

    private final static UiComponent hoverOverlay = new UiBlock();

    private final int index;
    private final InventoryComp inventory;

    public InventorySlot(int index, InventoryComp inventory) {
        this.index = index;
        this.inventory = inventory;
    }

    @Override
    protected void init() {
        color = Color.WHITE;
        texture = Assets.getTexture("src/assets/ui/inventorySlot.png");

        hoverOverlay.color = Color.WHITE;
        hoverOverlay.texture = Assets.getTexture("src/assets/ui/slotHoverOverlay.png");

        if (getItem() != null) {
            addInventoryItemIcon();
        }
    }

    @Override
    protected void updateSelf() {
        handleHoverOverlay();

        updateItemIcon();
        handleClick();
    }

    public ItemStack getItem() {
        return inventory.inventoryItems[index];
    }

    public InventoryItemIcon getItemUi() {
        for (UiComponent child : children) {
            if (child instanceof InventoryItemIcon) {
                return (InventoryItemIcon) child;
            }
        }
        return null;
    }

    public void setItem(ItemStack item) {
        inventory.inventoryItems[index] = item;
    }

    private void addInventoryItemIcon() {
        add(new InventoryItemIcon(getItem()), ConstraintUtils.margin(5));
    }

    public ItemStack[] getInventory() {
        return inventory.inventoryItems;
    }

    private void handleHoverOverlay() {
        if (isMouseOver() && !children.contains(hoverOverlay)) {
            add(hoverOverlay, ConstraintUtils.margin(-SCALE, -SCALE));
        } else if (!isMouseOver() && children.contains(hoverOverlay)) {
            remove(hoverOverlay);
        }
    }

    /**
     * Synchronizes the InventoryItemIcon component with the Inventory array:
     * <li>Add new itemUi</li>
     * <li>Update current itemUi</li>
     * <li>Remove itemUi when needed</li>
     */
    private void updateItemIcon() {
        InventoryItemIcon itemUi = getItemUi();
        ItemStack item = getItem();

        if (item != null) {
            if (itemUi == null && !InventoryManager.getFromCurrentScene().isHolding()) {
                addInventoryItemIcon();
            }
            if (itemUi != null && item != itemUi.getItem()) {
                remove(itemUi);
                addInventoryItemIcon();
            }
        }

        if (item == null) {
            if (itemUi != null) {
                remove(itemUi);
            }
        }
    }

    private void handleClick() {
        if (isMouseOver() && GameManager.gameControls.inventoryControls.isClicked(MouseButton.LEFT)) {
            InventoryManager.getFromCurrentScene().handleClick(this);
        }
    }
}
