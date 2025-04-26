package TDA.ui.components.inventory.itemInventory;

import TDA.ui.components.inventory.InventoryTab;
import TDA.ui.menus.inventory.itemList.InventoryItemList;
import woareXengine.ui.common.tabs.TabGroup;
import woareXengine.ui.components.UiBlock;
import woareXengine.ui.components.UiComponent;
import woareXengine.ui.constraints.CenterConstraint;
import woareXengine.ui.constraints.ConstraintUtils;
import woareXengine.ui.constraints.PixelConstraint;
import woareXengine.ui.constraints.UiConstraints;
import woareXengine.util.Assets;
import woareXengine.util.Color;

import static TDA.ui.components.inventory.itemInventory.InventorySlot.SCALE;
import static TDA.ui.menus.inventory.InventoryUiConfigs.BACKGROUND_COLOR;

public class InventoryUi extends UiComponent {
    @Override
    protected void init() {
        color = BACKGROUND_COLOR;

        TabGroup inventoryTabs = new TabGroup();
        add(inventoryTabs, ConstraintUtils.fill());
        inventoryTabs.addTab(
                new InventoryTab(Assets.getTexture("src/assets/ui/itemInventoryIcon.png")), new UiConstraints(
                        new PixelConstraint(71 * SCALE), new PixelConstraint(81 * SCALE),
                        new PixelConstraint(13 * SCALE), new PixelConstraint(13 * SCALE)
                ),
                new ItemInventoryList(), ConstraintUtils.fill(),
                true
        );
//
//        inventoryTabs.addTab(
//                new InventoryTab(Assets.getTexture("src/assets/ui/dinoInventoryIcon.png")), new UiConstraints(
//                        new PixelConstraint(71 * SCALE), new PixelConstraint(66 * SCALE),
//                        new PixelConstraint(13 * SCALE), new PixelConstraint(13 * SCALE)
//                ),
//                new UiBlock(Color.WHITE), new UiConstraints(
//                        new CenterConstraint(),
//                        new CenterConstraint(),
//                        new PixelConstraint(82 * 5),
//                        new PixelConstraint(78 * 5)
//                ),
//                false
//        );
//        add(new ItemInventoryList(), ConstraintUtils.fill());
    }

    @Override
    protected void updateSelf() {

    }
}
