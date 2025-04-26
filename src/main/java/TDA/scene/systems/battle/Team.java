package TDA.scene.systems.battle;

import TDA.entities.components.rendering.QuadComp;
import TDA.entities.dinos.Dino;
import woareXengine.mainEngine.Engine;

import java.util.Arrays;
import java.util.Objects;

public class Team {
    private final Dino[] dinos;

    public Team(Dino... dinos) {
        if (dinos.length != BattleConfigs.TEAM_SIZE) {
            throw new IllegalArgumentException("Team size must be " + BattleConfigs.TEAM_SIZE + " but was " + dinos.length);
        }
        this.dinos = dinos;

        for (int i = 0; i < dinos.length; i++) {
            if (dinos[i] == null) continue;
            positionDino(i);
        }
    }

    public Dino[] getDinos() {
        return Arrays.stream(dinos).filter(Objects::nonNull).toArray(Dino[]::new);
    }

    public boolean isDead() {
        for (Dino dino : dinos) {
            if (dino == null) continue;
            if (dino.battleHandler().isAlive()) {
                return false;
            }
        }
        return true;
    }

    public boolean isAlliedTeam() {
        return this instanceof AlliedTeam;
    }

    public void removeDino(Dino dino) {
        for (int i = 0; i < dinos.length; i++) {
            if (dinos[i] == dino) {
                dinos[i] = null;
                return;
            }
        }
    }

    /**
     * Positions the dino at the correct position based on the index and the team.
     *
     * @param index the index of the dino in the team
     */
    private void positionDino(int index) {
        if (dinos[index] == null) return;
        float x, y;

        if (isAlliedTeam()) {
            if (BattleConfigs.isFrontLine(index)) {
                x = BattleConfigs.FrontLine.X_OFFSET;
                y = (Engine.window().getPixelHeight() * BattleConfigs.Y_AVAILABLE_SPACE) / (BattleConfigs.FRONT_LINE_SIZE + 1) * (index + 1);
            } else {
                x = BattleConfigs.BackLine.X_OFFSET;
                int backLineIndex = index - BattleConfigs.FRONT_LINE_SIZE;
                y = (Engine.window().getPixelHeight() * BattleConfigs.Y_AVAILABLE_SPACE) / (BattleConfigs.BACK_LINE_SIZE + 1) * (backLineIndex + 1);
            }
        } else {
            dinos[index].getComponent(QuadComp.class).quad.horizontalFlip();

            if (BattleConfigs.isFrontLine(index)) {
                x = Engine.window().getPixelWidth() - dinos[index].transform.getWidth() - BattleConfigs.FrontLine.X_OFFSET;
                y = (Engine.window().getPixelHeight() * BattleConfigs.Y_AVAILABLE_SPACE) / (BattleConfigs.FRONT_LINE_SIZE + 1) * (index + 1);
            } else {
                x = Engine.window().getPixelWidth() - dinos[index].transform.getWidth() - BattleConfigs.BackLine.X_OFFSET;
                int backLineIndex = index - BattleConfigs.FRONT_LINE_SIZE;
                y = (Engine.window().getPixelHeight() * BattleConfigs.Y_AVAILABLE_SPACE) / (BattleConfigs.BACK_LINE_SIZE + 1) * (backLineIndex + 1);
            }
        }
        dinos[index].transform.setPosition(x, y);
    }


    public static class AlliedTeam extends Team {
        public AlliedTeam(Dino... dinos) {
            super(dinos);
        }
    }

    public static class EnemyTeam extends Team {
        public EnemyTeam(Dino... dinos) {
            super(dinos);
        }
    }
}
