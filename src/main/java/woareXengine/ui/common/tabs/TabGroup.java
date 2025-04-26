package woareXengine.ui.common.tabs;

import woareXengine.io.userInputs.Input;
import woareXengine.io.userInputs.MouseButton;
import woareXengine.ui.components.UiComponent;
import woareXengine.ui.constraints.UiConstraints;

import java.util.HashMap;
import java.util.Map;

public class TabGroup extends UiComponent {
    private Map<UiComponent, UiComponent> tabs = new HashMap<>();
    private UiComponent selectedKey = null;


    @Override
    protected void init() {
        updateShow();
    }

    @Override
    protected void updateSelf() {
        for (UiComponent tab : tabs.keySet()) {
            if (tab.isMouseOver() && Input.mouse().isClickEvent(MouseButton.LEFT)) {
                System.out.println("CLICKED");
                selectedKey = tab;
                updateShow();
            }
        }
    }

    private void updateShow() {
        for (UiComponent tab : tabs.keySet()) {
            tabs.get(tab).show(selectedKey == tab);
        }
    }

    public void addTab(UiComponent tabButton, UiConstraints buttonConstraints,
                       UiComponent tabContent, UiConstraints contentConstraints,
                       boolean showTabContentOnInit) {
        add(tabButton, buttonConstraints);
        add(tabContent, contentConstraints);
        tabs.put(tabButton, tabContent);
        tabContent.show(showTabContentOnInit);
        if (showTabContentOnInit) {
            selectedKey = tabButton;
        }
    }

    public UiComponent getSelectedTab() {
        return selectedKey;
    }
}
