package TDA.ui.states;

import TDA.entities.dinos.Dino;
import TDA.main.GameManager;
import TDA.scene.systems.battle.BattleContext;
import TDA.ui.TDAUi;
import woareXengine.ui.common.ProgressBar;
import woareXengine.ui.components.UiBlock;
import woareXengine.ui.constraints.CenterConstraint;
import woareXengine.ui.constraints.PixelConstraint;
import woareXengine.ui.constraints.UiConstraints;
import woareXengine.ui.text.basics.Text;
import woareXengine.util.Assets;
import woareXengine.util.Color;

import java.util.HashMap;
import java.util.Map;

public class BattleUiState extends UiState {
    private final BattleContext battleContext;

    private final Map<Integer, ProgressBar> healthBars = new HashMap<>();
    private Text roundText;

    public BattleUiState(BattleContext battleContext) {
        super();
        this.battleContext = battleContext;
    }

    @Override
    protected void toggleMouseAndKeyboard(boolean isOpening) {
        GameManager.gameControls.playerControls.enableInput(!isOpening);
        GameManager.gameControls.inventoryControls.enableInput(!isOpening);
    }

    @Override
    public void enableState(boolean open) {
        super.enableState(open);

        TDAUi.get().gameUi.mainGame.enableState(!open);
        TDAUi.get().gameUi.inventory.enableState(false);
    }

    @Override
    protected void init() {
        for (Dino dino : battleContext.orderedDinosList()) {
            ProgressBar healthBar = new ProgressBar();
            healthBar.backgroundColor = new Color("#eeeeee");
            healthBar.progressColor = new Color("#00ff00");
            healthBar.progress = dino.battleHandler().getHealthPercentage();

            healthBars.put(dino.getId(), healthBar);

            add(healthBar, new UiConstraints(
                    new PixelConstraint((int) (dino.transform.getCenter().x - 75)),
                    new PixelConstraint((int) dino.transform.getY() + 5),
                    new PixelConstraint(150),
                    new PixelConstraint(10)
            ));
        }


        roundText = Assets.getDefaultFont().createText("Round 1", 0.5f);
        UiBlock roundTextBlock = new UiBlock(Color.WHITE);
        add(roundTextBlock, new UiConstraints(
                new CenterConstraint(),
                new PixelConstraint(10, true),
                new PixelConstraint(100),
                new PixelConstraint(30)
        ));
        roundTextBlock.add(roundText, new UiConstraints(
                new CenterConstraint(),
                new CenterConstraint()
        ));
    }

    @Override
    protected void updateSelf() {
        roundText.textString = "Round " + (battleContext.getRound() + 1);
        roundText.getConstraints().setWidth(new PixelConstraint((int) roundText.calculateWidth()));
        roundText.getConstraints().setHeight(new PixelConstraint((int) roundText.calculateHeight()));
        roundText.getConstraints().notifyAdded(roundText, roundText.getParent());
    }

    public void updateHealthBar(Dino dino) {
        ProgressBar healthBar = healthBars.get(dino.getId());
        healthBar.setProgress(dino.battleHandler().getHealthPercentage());
    }

    public void handleDeath(Dino dino) {
        remove(healthBars.get(dino.getId()));
    }
}
