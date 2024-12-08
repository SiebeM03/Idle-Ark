package testGame;

import engine.ecs.GameObject;
import engine.ui.MouseEventConsumer;


public abstract class Resource extends MouseEventConsumer {
    private String name;
    private float amount;
    private float amountPerClick = 1;
    protected transient GameObject toolTipGo;

    @Override
    public void update() {
        updateClickDelayTimer();

    }

    @Override
    public void onClick() {
        if (!canClick()) return;
        harvest();
        resetClickDelayTimer();
    }

    @Override
    public void onHover() {
    }

    @Override
    public void onEnter() {
    }

    @Override
    public void onLeave() {
    }

    private void harvest() {
        this.setAmount(this.amount() + this.amountPerClick());
        System.out.println(this.name() + " has " + this.amount() + " resources");
    }

    public String name() {
        return name;
    }

    public float amount() {
        return amount;
    }

    public float amountPerClick() {
        return amountPerClick;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
