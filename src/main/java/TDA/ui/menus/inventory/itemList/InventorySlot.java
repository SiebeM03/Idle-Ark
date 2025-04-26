package TDA.ui.menus.inventory.itemList;

import TDA.entities.player.PlayerPrefab;
import TDA.scene.systems.InventoryManagerOld;
import TDA.entities.inventory.items.ItemStack;
import TDA.main.GameManager;
import TDA.ui.menus.hotbar.HotbarSlotWrapper;
import woareXengine.io.userInputs.MouseButton;
import woareXengine.ui.common.UiBorderedBlock;
import woareXengine.ui.components.UiComponent;
import woareXengine.ui.constraints.ConstraintUtils;
import woareXengine.ui.text.basics.Font;
import woareXengine.ui.text.basics.Text;

import static TDA.ui.menus.inventory.InventoryUiConfigs.*;

public class InventorySlot extends UiBorderedBlock {
    private boolean selected = false;

    private int index;

    public InventorySlot(int index) {
        this.index = index;
    }

    @Override
    protected void init() {
        super.init();

        setFillColor(INVENTORY_UI_COLOR);
        setBorderColor(ITEM_BORDER_COLOR);
        setBorderWidth(ITEM_BORDER_WIDTH);

        if (getItemStack() != null) {
            add(new InventoryItemUi(), ConstraintUtils.fill());
        }

        if (parent instanceof HotbarSlotWrapper) {
            selected = index == PlayerPrefab.getHotbar().getSelectedIndex();
        }
    }

    @Override
    protected void updateSelf() {
        super.updateSelf();
        selected = parent instanceof HotbarSlotWrapper && index == PlayerPrefab.getHotbar().getSelectedIndex();

        // If a new item is found that is not yet added to the UI, add it
        if (getItemStack() != null && getInventoryItem() == null && !InventoryManagerOld.getFromCurrentScene().isHolding()) {
            System.out.println(index + " has a new item");
            add(new InventoryItemUi(), ConstraintUtils.fill());
        }

        // If an item's amount reaches 0, remove it from the UI
        if (getItemStack() == null && getInventoryItem() != null) {
            System.out.println(index + " removed item");
            InventoryItemUi itemUi = getInventoryItem();
            Text amountText = itemUi.amountText;
            Font.texts.get(amountText.font).remove(amountText);
            remove(getInventoryItem());
        }
        handleHolding();
    }

    /**
     * Handles the placing/swapping of items between inventories. Also works across multiple inventories (e.g. PlayerInventory <-> Hotbar)
     * <ul>
     * <li>Slot is clicked while {@link InventoryManagerOld} is holding an item:
     * <ul>
     *     <li>Clicked slot is empty? The item is placed on the clicked slot</li>
     *     <li>Clicked slot is not empty? Both items swap places</li>
     * </ul></li>
     * <li>Slot is clicked while {@link InventoryManagerOld} is not holding an item:
     * <ul>
     *     <li>The item inside the slot is picked up</li>
     *     <li>If there is no item, nothing happens</li>
     * </ul></li></ul>
     */
    private void handleHolding() {
        if (isMouseOver() && GameManager.gameControls.inventoryControls.isClicked(MouseButton.LEFT)) {
            if (InventoryManagerOld.getFromCurrentScene().isHolding()) {
                // If holding an item, place it on the clicked slot
                InventoryManagerOld.getFromCurrentScene().placeHolding(getIndex(), this);
            } else {
                // If not holding an item, pick up the item in the slot (if any)
                if (getInventoryItem() == null) return;
                InventoryManagerOld.getFromCurrentScene().setHolding(getIndex(), this);
            }
        }
    }

    public InventoryItemUi getInventoryItem() {
        for (UiComponent c : children) {
            if (c instanceof InventoryItemUi) {
                return (InventoryItemUi) c;
            }
        }
        return null;
    }

    public ItemStack getItemStack() {
        if (parent instanceof InventoryItemList) {
            return ((InventoryItemList) parent).getItemStack(index);
        }
        if (parent instanceof HotbarSlotWrapper) {
            return ((HotbarSlotWrapper) parent).getItemStack(index);
        }
        return null;
    }

    public int getIndex() {
        return index;
    }
}
