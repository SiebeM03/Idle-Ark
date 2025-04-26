package TDA.ui.menus;

import woareXengine.ui.common.ProgressBar;
import woareXengine.ui.common.tabs.TabGroup;
import woareXengine.ui.components.UiBlock;
import woareXengine.ui.components.UiComponent;
import woareXengine.ui.constraints.*;
import woareXengine.util.Color;

public class TestUi extends UiComponent {

    @Override
    protected void init() {
        TabGroup tabGroup = new TabGroup();

        add(tabGroup, ConstraintUtils.fill());
        tabGroup.show(true);

    }

    @Override
    protected void updateSelf() {

    }
}
