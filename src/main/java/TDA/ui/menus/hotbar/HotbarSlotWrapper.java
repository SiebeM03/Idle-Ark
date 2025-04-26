package TDA.ui.menus.hotbar;

import TDA.entities.player.PlayerPrefab;
import TDA.entities.inventory.items.ItemStack;
import TDA.ui.menus.inventory.itemList.InventorySlot;
import woareXengine.ui.components.UiComponent;
import woareXengine.ui.constraints.PixelConstraint;
import woareXengine.ui.constraints.UiConstraints;

import static TDA.ui.menus.inventory.InventoryUiConfigs.*;

public class HotbarSlotWrapper extends UiComponent {
    private final ItemStack[] hotbarItems;

    public HotbarSlotWrapper() {
        this.hotbarItems = PlayerPrefab.getHotbar().getHotbarItems();
    }

    @Override
    protected void init() {
        for (int i = 0; i < hotbarItems.length; i++) {
            add(new InventorySlot(i), new UiConstraints(
                    new PixelConstraint(i * (ITEM_SIZE + ITEM_SPACING)),
                    new PixelConstraint(0),
                    new PixelConstraint(ITEM_SIZE),
                    new PixelConstraint(ITEM_SIZE)
            ));
        }
    }

    @Override
    protected void updateSelf() {

    }

    public ItemStack getItemStack(int index) {
        return hotbarItems[index];
    }

    private InventorySlot getSlot(int index) {
        for (UiComponent c : children) {
            if (!(c instanceof InventorySlot)) continue;
            InventorySlot i = (InventorySlot) c;

            if (i.getIndex() == index) {
                return i;
            }
        }
        return null;
    }
}
