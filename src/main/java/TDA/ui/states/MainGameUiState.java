package TDA.ui.states;

import TDA.entities.inventory.items.ItemStack;
import TDA.entities.player.PlayerPrefab;
import TDA.ui.menus.hotbar.HotbarUi;
import TDA.ui.menus.newInventoryItemPopup.ItemPopupContainer;
import woareXengine.ui.constraints.*;

import static TDA.ui.menus.inventory.InventoryUiConfigs.*;

public class MainGameUiState extends UiState {

    private HotbarUi hotbarUi = new HotbarUi();
    private ItemPopupContainer newItemContainer = new ItemPopupContainer();


    @Override
    protected void toggleMouseAndKeyboard(boolean isOpening) {

    }

    @Override
    protected void init() {
        int hotbarSize = PlayerPrefab.getHotbar().getHotbarItems().length;
        add(hotbarUi, new UiConstraints(
                new CenterConstraint(),
                new PixelConstraint(0),
                new PixelConstraint((ITEM_SIZE + ITEM_SPACING) * hotbarSize),
                new PixelConstraint(ITEM_SIZE + ITEM_SPACING)
        ));

        add(newItemContainer, ConstraintUtils.fill());
    }

    @Override
    protected void updateSelf() {

    }

    public void itemAddedToInventory(ItemStack itemStack) {
        newItemContainer.createPopup(itemStack);
    }
}
