package TDA.ui.states;

import TDA.entities.storage.StorageComp;
import TDA.main.GameManager;
import TDA.scene.systems.InventoryManager;
import TDA.scene.systems.InventoryManagerOld;
import TDA.ui.components.inventory.InventoryTab;
import TDA.ui.components.inventory.itemInventory.InventoryUi;
import TDA.ui.menus.inventory.PlayerInventoryUi;
import TDA.ui.menus.inventory.itemList.InventoryItemList;
import woareXengine.ui.common.tabs.TabGroup;
import woareXengine.ui.components.UiBlock;
import woareXengine.ui.components.UiComponent;
import woareXengine.ui.constraints.ConstraintUtils;
import woareXengine.ui.constraints.PixelConstraint;
import woareXengine.ui.constraints.UiConstraints;
import woareXengine.util.Color;

import static TDA.ui.components.inventory.itemInventory.InventorySlot.SCALE;

public class InventoryUiState extends UiState {
    private UiComponent inventoryBackground = new UiBlock();
    private PlayerInventoryUi playerInventoryUi = new PlayerInventoryUi();

    @Override
    protected void toggleMouseAndKeyboard(boolean isOpening) {
        GameManager.gameControls.windowControls.enableInput(!isOpening);
        GameManager.gameControls.playerControls.enableInput(!isOpening);

        if (!isOpening) {
            InventoryManager.getFromCurrentScene().resetHolding();
        }

        if (!isOpening && playerInventoryUi.storageInventoryItemList != null) {
            playerInventoryUi.rightSegment.children.forEach(this::remove);
            InventoryManagerOld.getFromCurrentScene().setExternalInventory(null);
        }
    }

    @Override
    protected void init() {
        add(new InventoryUi(), ConstraintUtils.fill());

//        add(playerInventoryUi, ConstraintUtils.fill());
        enableState(false);
    }

    @Override
    protected void updateSelf() {

    }

    public void showStorage(StorageComp storage) {
        playerInventoryUi.showStorageInventory(new InventoryItemList(storage.getInventory()), storage.storageName);
        InventoryManagerOld.getFromCurrentScene().setExternalInventory(storage.getInventory());
        enableState(true);
    }
}
