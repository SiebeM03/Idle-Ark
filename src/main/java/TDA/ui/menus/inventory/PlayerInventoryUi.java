package TDA.ui.menus.inventory;

import TDA.entities.inventory.InventoryComp;
import TDA.entities.player.PlayerPrefab;
import TDA.ui.menus.inventory.itemList.InventoryItemList;
import woareXengine.ui.components.UiBlock;
import woareXengine.ui.components.UiComponent;
import woareXengine.ui.constraints.*;
import woareXengine.util.Assets;

import static TDA.ui.menus.inventory.InventoryUiConfigs.*;

public class PlayerInventoryUi extends UiComponent {

    public InventoryComp inventory;
    public InventoryItemList storageInventoryItemList;

    public final UiBlock leftSegment = new UiBlock();
    public final UiBlock middleSegment = new UiBlock();
    public final UiBlock rightSegment = new UiBlock();

    public PlayerInventoryUi() {
        this.inventory = PlayerPrefab.getInventory();
    }

    @Override
    protected void init() {
        InventoryItemList playerInventory = new InventoryItemList(inventory);
        playerInventory.isPlayerInventory(true);

        add(playerInventory, new UiConstraints(
                new CenterConstraint(),
                new CenterConstraint(),
                new PixelConstraint(350),
                new PixelConstraint(290)
        ));

//        UiBlock container = new UiBlock();
//        add(container, ConstraintUtils.margin(16));

//        createPlayerInventory(container);
//        createMiddleSegment(container);
//        createRightSegment(container);
    }

//    private void createPlayerInventory(UiBlock container) {
//        container.add(leftSegment, ConstraintUtils.fill().setWidth(new RelativeConstraint(1 / 3f)));
//
//        InventoryItemList playerInventory = new InventoryItemList(inventory);
//        playerInventory.isPlayerInventory(true);
//
//        leftSegment.add(playerInventory, ConstraintUtils.fill(341, 457));
//
//
//        leftSegment.add(Assets.getDefaultFont().createText("Player inventory", 0.7f), new UiConstraints(
//                new PositionConstraint(Position.LEFT),
//                new PixelConstraint(0, true),
//                new PixelConstraint(341),
//                new PixelConstraint(24)
//        ));
//    }
//
//    private void createMiddleSegment(UiBlock container) {
//        container.add(middleSegment, ConstraintUtils.fill().setWidth(new RelativeConstraint(1 / 3f)).setX(new RelativeConstraint(1 / 3f)));
//    }
//
//    private void createRightSegment(UiBlock container) {
//        container.add(rightSegment, ConstraintUtils.fill().setWidth(new RelativeConstraint(1 / 3f)).setX(new RelativeConstraint(2 / 3f)));
//    }

    @Override
    protected void updateSelf() {

    }

    public void showStorageInventory(InventoryItemList storageInventoryItemList, String storageName) {
        this.storageInventoryItemList = storageInventoryItemList;
        rightSegment.add(storageInventoryItemList, ConstraintUtils.fill(341, 457).setX(new PositionConstraint(Position.RIGHT)));

        rightSegment.add(Assets.getDefaultFont().createText(storageName + " inventory", 0.7f), new UiConstraints(
                new PositionConstraint(Position.RIGHT),
                new PixelConstraint(0, true),
                new PixelConstraint(341),
                new PixelConstraint(24)
        ));
    }
}
