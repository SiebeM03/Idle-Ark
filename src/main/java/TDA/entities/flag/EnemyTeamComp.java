package TDA.entities.flag;

import TDA.entities.components.interactions.ClickableComp;
import TDA.entities.dinos.Dino;
import TDA.entities.dinos.types.rex.Rex;
import TDA.main.GameManager;
import TDA.scene.systems.battle.BattleContext;
import TDA.scene.systems.battle.Team;
import woareXengine.io.userInputs.Input;
import woareXengine.io.userInputs.MouseButton;

import java.util.Arrays;

public class EnemyTeamComp extends ClickableComp {
    private final Team.EnemyTeam team;

    public EnemyTeamComp(Dino... dinos) {
        this.team = new Team.EnemyTeam(dinos);
    }

    @Override
    public void update() {
        if (isMouseOver() && Input.mouse().isClickEvent(MouseButton.LEFT)) {
            System.out.println("Starting battle against: " + Arrays.toString(team.getDinos()));
            GameManager.startBattle(new BattleContext(new Team.AlliedTeam(new Rex().withStats(100, 20, 160), null, null, null, null), team));
        }
    }
}
