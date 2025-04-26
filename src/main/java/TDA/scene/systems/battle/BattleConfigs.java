package TDA.scene.systems.battle;

public class BattleConfigs {
    public static final int TEAM_SIZE = 5;
    public static final int FRONT_LINE_SIZE = 2;
    public static final int BACK_LINE_SIZE = TEAM_SIZE - FRONT_LINE_SIZE;

    public static final float BATTLE_SPEED = 20.0f;


    public static class FrontLine {
        public static final int X_OFFSET = 300;
    }

    public static class BackLine {
        public static final int X_OFFSET = 100;
    }

    public static final float Y_AVAILABLE_SPACE = 0.7f;

    public static boolean isFrontLine(int index) {
        return index <= FRONT_LINE_SIZE - 1;
    }
}
