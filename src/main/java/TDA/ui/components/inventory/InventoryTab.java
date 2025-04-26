package TDA.ui.components.inventory;

import TDA.ui.components.inventory.itemInventory.InventorySlot;
import woareXengine.openglWrapper.textures.Texture;
import woareXengine.ui.components.UiBlock;
import woareXengine.ui.components.UiComponent;
import woareXengine.ui.constraints.ConstraintUtils;
import woareXengine.util.Assets;
import woareXengine.util.Color;

public class InventoryTab extends UiComponent {
    private final Texture icon;

    private final static UiComponent hoverOverlay = new UiBlock();

    public InventoryTab(Texture icon) {
        this.icon = icon;
    }

    @Override
    protected void init() {
        color = Color.WHITE;
        this.texture = Assets.getTexture("src/assets/ui/inventoryTabSlot.png");

        hoverOverlay.color = Color.WHITE;
        hoverOverlay.texture = Assets.getTexture("src/assets/ui/tabHoverOverlay.png");

        UiBlock iconBlock = new UiBlock();
        iconBlock.color = Color.WHITE;
        iconBlock.texture = icon;
        add(iconBlock, ConstraintUtils.margin(2 * InventorySlot.SCALE));
        // TODO this prevents slot 1 from being displayed
    }

    @Override
    protected void updateSelf() {

    }
}
