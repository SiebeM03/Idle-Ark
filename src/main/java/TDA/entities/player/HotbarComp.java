package TDA.entities.player;

import TDA.entities.inventory.InventoryComp;
import TDA.entities.main.Component;
import TDA.entities.inventory.items.ItemStack;
import TDA.entities.resources.items.types.StoneItem;
import TDA.entities.resources.items.types.TreeItem;
import TDA.entities.tools.Axe;
import TDA.entities.tools.Pickaxe;
import TDA.main.GameManager;
import woareXengine.util.Logger;

public class HotbarComp extends InventoryComp {
    private int selected = 0;

    public HotbarComp() {
        super(10);
    }

    @Override
    public void init() {
        inventoryItems[0] = new ItemStack(new Pickaxe(), 1);
        inventoryItems[1] = new ItemStack(new Axe(), 1);
        inventoryItems[2] = new ItemStack(new TreeItem(), 120);
        inventoryItems[4] = new ItemStack(new StoneItem(), 40);
    }

    @Override
    public void update() {
        super.update();

        int buttonPressed = GameManager.gameControls.inventoryControls.isHotbarItemSelected();
        if (buttonPressed == -1) return;
        selected = buttonPressed;
    }

    public ItemStack[] getHotbarItems() {
        return inventoryItems;
    }

    public ItemStack getSelectedItem() {
        return inventoryItems[selected];
    }

    public int getSelectedIndex() {
        return selected;
    }

    @Override
    public void addItem(ItemStack itemStack) {
        throw new UnsupportedOperationException("Cannot add items to hotbar directly. Use InventoryManager instead.");
    }
}
