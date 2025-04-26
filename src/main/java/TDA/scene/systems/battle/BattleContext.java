package TDA.scene.systems.battle;

import TDA.entities.components.rendering.QuadComp;
import TDA.entities.dinos.BattleHandler;
import TDA.entities.dinos.Dino;
import TDA.main.GameManager;
import TDA.scene.SceneSystem;
import TDA.scene.systems.battle.Team.AlliedTeam;
import TDA.scene.systems.battle.Team.EnemyTeam;
import TDA.ui.TDAUi;
import TDA.ui.states.BattleUiState;
import woareXengine.mainEngine.Engine;
import woareXengine.ui.constraints.ConstraintUtils;
import woareXengine.util.Color;
import woareXengine.util.Delay;
import woareXengine.util.Id;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BattleContext extends SceneSystem {
    private static final Id ID = new Id();

    protected final Team alliedTeam;
    protected final Team enemyTeam;

    private final List<Dino> dinosInAttackOrder = new ArrayList<>();

    private BattleState state = BattleState.INITIALIZING;
    private BattleUiState battleUiState;

    private int round = 0;
    private int dinoIndex = 0;
    private Delay timeUntilNextAbility = new Delay(0);
    private Dino currentDino;

    public BattleContext(AlliedTeam alliedTeam, EnemyTeam enemyTeam) {
        super(ID);
        this.alliedTeam = alliedTeam;
        this.enemyTeam = enemyTeam;
        BattleUtil.setContext(this);

        for (Dino dino : orderedDinosList()) {
            dino.setBattleHandler(new BattleHandler(dino));
        }
        initUi();
    }

    Delay startDelay = new Delay(2);

    @Override
    protected void update() {
        if (!startDelay.isOver()) return;
        Engine.setGameSpeed(BattleConfigs.BATTLE_SPEED);

        for (Dino dino : orderedDinosList()) {
            dino.update();
        }

        if (state == BattleState.INITIALIZING) {
            start();
            state = BattleState.IN_PROGRESS;
            return;
        }

        if (state == BattleState.IN_PROGRESS) {
            if (alliedTeam.isDead()) {
                state = BattleState.DEFEAT;
                return;
            }

            if (enemyTeam.isDead()) {
                state = BattleState.VICTORY;
                return;
            }

            handleRound();
            return;
        }

        if (battleEnded()) {
            Engine.setGameSpeed(1);
            finish();
        }
    }

    @Override
    protected void end() {

    }

    @Override
    public void cleanUp() {
        battleUiState.enableState(false);
        TDAUi.get().gameUi.remove(battleUiState);
    }

    // =========================================== Battle Lifecycle Methods ============================================
    public void start() {
        for (Dino dino : orderedDinosList()) {
            dino.battleHandler().startBattle(this);
        }
    }

    private void newRound() {
        round++;
        timeUntilNextAbility.reset().stop();
        dinoIndex = 0;
    }

    public void handleRound() {
        if (!timeUntilNextAbility.isOver()) return;   // If the ability is not over, do nothing
        if (dinoIndex >= dinosInAttackOrder.size()) {
            newRound();
        }

        if (currentDino != null) {
            // Reset the color of the previous dino
            currentDino.getComponent(QuadComp.class).quad.color = Color.WHITE;
        }

        currentDino = dinosInAttackOrder.get(dinoIndex);

        timeUntilNextAbility = new Delay(currentDino.abilityHandler().onActive(this));
        timeUntilNextAbility.start();
        currentDino.getComponent(QuadComp.class).quad.color = Color.CYAN;

        dinoIndex++;
    }

    public void finish() {
        for (Dino dino : orderedDinosList()) {
            if (dino.battleHandler() == null) continue;
            dino.battleHandler().finishBattle(this);
            dino.setBattleHandler(null);
        }
    }


    // =========================================== Battle Utility Methods ==============================================
    public List<Dino> orderedDinosList() {
        dinosInAttackOrder.clear();
        dinosInAttackOrder.addAll(Arrays.asList(alliedTeam.getDinos()));
        dinosInAttackOrder.addAll(Arrays.asList(enemyTeam.getDinos()));
        dinosInAttackOrder.sort((d1, d2) -> {
            if (d1.getStats().speedStat.getActualValue() > d2.getStats().speedStat.getActualValue()) {
                return -1;
            } else if (d1.getStats().speedStat.getActualValue() < d2.getStats().speedStat.getActualValue()) {
                return 1;
            }
            return 0;
        });
        return dinosInAttackOrder;
    }

    public void handleDeath(Dino dino) {
        BattleUtil.getAlliedTeam(dino).removeDino(dino);    // Remove the dino from the team
        dinosInAttackOrder.remove(dino);                    // Remove the dino from the attack order

        GameManager.currentScene.renderer.removeQuad(dino.getComponent(QuadComp.class).quad);   // Remove the dino from the renderer
        battleUiState.handleDeath(dino);                    // Remove the health bar from the UI
    }

    public boolean battleEnded() {
        return state == BattleState.VICTORY || state == BattleState.DEFEAT;
    }

    public int getRound() {
        return round;
    }


    // =========================================== Ui ==================================================================
    private void initUi() {
        this.battleUiState = new BattleUiState(this);
        this.battleUiState.enableState(true);
        TDAUi.get().gameUi.add(battleUiState, ConstraintUtils.fill());
    }

    public void updateHealthBar(Dino dino) {
        battleUiState.updateHealthBar(dino);
    }
}
