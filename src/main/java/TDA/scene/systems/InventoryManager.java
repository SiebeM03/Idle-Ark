package TDA.scene.systems;

import TDA.entities.inventory.items.ItemStack;
import TDA.main.GameManager;
import TDA.scene.SceneSystem;
import TDA.ui.components.inventory.itemInventory.InventoryItemIcon;
import TDA.ui.components.inventory.itemInventory.InventorySlot;
import org.joml.Vector2f;
import woareXengine.io.userInputs.Input;
import woareXengine.io.userInputs.MouseButton;
import woareXengine.ui.main.Ui;
import woareXengine.util.Id;

public class InventoryManager extends SceneSystem {
    private static final Id ID = new Id();

    private InventorySlot sourceSlot;

    private InventoryItemIcon holdingUi;


    public InventoryManager() {
        super(ID);
    }

    @Override
    protected void update() {
        if (holdingUi == null) return;

        snapToMouse();

        if (GameManager.gameControls.inventoryControls.isClicked(MouseButton.RIGHT)) {
            resetHolding();
        }
    }

    @Override
    protected void end() {

    }

    @Override
    protected void cleanUp() {

    }

    public void handleClick(InventorySlot clickedSlot) {
        if (holdingUi == null) {
            pickupItem(clickedSlot);
        } else {
            placeItem(clickedSlot);
        }
    }

    public void resetHolding() {
        if (holdingUi == null) return;
        holdingUi.setParent(sourceSlot);

        sourceSlot = null;
        holdingUi = null;
    }

    private void pickupItem(InventorySlot sourceSlot) {
        if (sourceSlot.getItem() == null) return;

        holdingUi = sourceSlot.getItemUi();
        if (holdingUi == null) return;
        holdingUi.show(false);

        this.sourceSlot = sourceSlot;
        Vector2f size = holdingUi.transform.copy().getDimensions();
        holdingUi.setParent(Ui.getContainer());
        holdingUi.transform.setWidth(size.x);
        holdingUi.transform.setHeight(size.y);

        snapToMouse();
        holdingUi.show(true);
    }

    private void placeItem(InventorySlot targetSlot) {
        ItemStack tempItem = sourceSlot.getItem();
        sourceSlot.setItem(targetSlot.getItem());
        targetSlot.setItem(tempItem);
        Ui.getContainer().remove(holdingUi);

        sourceSlot = null;
        holdingUi = null;
    }

    private void snapToMouse() {
        holdingUi.transform.setX(Input.mouse().getX() * Ui.getContainer().transform.getWidth() - holdingUi.transform.getWidth() / 2);
        holdingUi.transform.setY(Input.mouse().getY() * Ui.getContainer().transform.getHeight() - holdingUi.transform.getHeight() / 2);
    }

    public boolean isHolding() {
        return holdingUi != null;
    }

    public static InventoryManager getFromCurrentScene() {
        return (InventoryManager) GameManager.currentScene.getSystem(ID);
    }
}
