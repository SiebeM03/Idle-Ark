package TDA.ui.menus.inventory.itemList;

import TDA.entities.inventory.InventoryComp;
import TDA.entities.inventory.items.ItemStack;
import woareXengine.ui.components.UiComponent;
import woareXengine.ui.constraints.PixelConstraint;
import woareXengine.ui.constraints.UiConstraints;

import static TDA.ui.menus.inventory.InventoryUiConfigs.*;

public class InventoryItemList extends UiComponent {
    public static final int COLS = 6;

    private final ItemStack[] inventoryItems;
    private boolean isPlayerInventory = false;

    public InventoryItemList(InventoryComp inventory) {
        this.inventoryItems = inventory.inventoryItems;
    }

    @Override
    protected void init() {
        for (int i = 0; i < inventoryItems.length; i++) {
            int column = i % COLS;
            int row = i / COLS;

            add(new InventorySlot(i), new UiConstraints(
                    new PixelConstraint(column * (ITEM_SIZE + ITEM_SPACING)),
                    new PixelConstraint(row * (ITEM_SIZE + ITEM_SPACING), true),
                    new PixelConstraint(ITEM_SIZE),
                    new PixelConstraint(ITEM_SIZE)
            ));
        }
    }

    @Override
    protected void updateSelf() {

    }

    public ItemStack getItemStack(int index) {
        return inventoryItems[index];
    }

    public void isPlayerInventory(boolean b) {
        isPlayerInventory = b;
    }

    public boolean isPlayerInventory() {
        return isPlayerInventory;
    }
}
