package TDA.ui.components.inventory.itemInventory;

import TDA.entities.inventory.InventoryComp;
import TDA.entities.player.PlayerPrefab;
import woareXengine.ui.components.UiBlock;
import woareXengine.ui.components.UiComponent;
import woareXengine.ui.constraints.CenterConstraint;
import woareXengine.ui.constraints.PixelConstraint;
import woareXengine.ui.constraints.UiConstraints;
import woareXengine.ui.text.basics.Text;
import woareXengine.util.Assets;
import woareXengine.util.Color;

public class ItemInventoryList extends UiComponent {
    UiBlock inventoryBackground = new UiBlock();
    @Override
    protected void init() {
//        UiComponent inventoryBackground = new UiBlock();
        inventoryBackground.color = Color.WHITE;
        inventoryBackground.texture = Assets.getTexture("src/assets/ui/inventoryBg.png");
        add(inventoryBackground, new UiConstraints(
                new CenterConstraint(),
                new CenterConstraint(),
                new PixelConstraint(82 * 5),
                new PixelConstraint(78 * 5)
        ));

        Text inventoryText = Assets.getDefaultFont().createText("Inventory", 1.3f);
        inventoryText.color = new Color("#5b6ee1");
        inventoryBackground.add(inventoryText, new UiConstraints(
                new CenterConstraint(),
                new PixelConstraint(6 * 5, true),
                new PixelConstraint((int) inventoryText.calculateWidth()),
                new PixelConstraint((int) inventoryText.calculateHeight())
        ));

        int columns = 6;
        int slotSize = InventorySlot.PIXEL_SIZE * InventorySlot.SCALE;
        int yOffsetTop = 15 * InventorySlot.SCALE;
        int xOffsetLeft = 6 * InventorySlot.SCALE;
        int margin = 2 * InventorySlot.SCALE;

        InventoryComp playerInventory = PlayerPrefab.getInventory();
        for (int i = 0; i < playerInventory.inventoryItems.length; i++) {
            inventoryBackground.add(new InventorySlot(i, playerInventory), new UiConstraints(
                    new PixelConstraint(xOffsetLeft + (i % columns) * (slotSize + margin)),
                    new PixelConstraint(yOffsetTop + (i / columns) * (slotSize + margin), true),
                    new PixelConstraint(slotSize),
                    new PixelConstraint(slotSize)
            ));
        }
    }

    @Override
    protected void updateSelf() {

    }
}
