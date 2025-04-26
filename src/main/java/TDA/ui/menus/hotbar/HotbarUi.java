package TDA.ui.menus.hotbar;

import woareXengine.ui.components.UiComponent;
import woareXengine.ui.constraints.ConstraintUtils;
import woareXengine.util.Color;

public class HotbarUi extends UiComponent {

    @Override
    protected void init() {
        color = new Color("#0061a6");
        color.setAlpha(0.8f);

        add(new HotbarSlotWrapper(), ConstraintUtils.margin(4, 4));
    }

    @Override
    protected void updateSelf() {

    }
}
