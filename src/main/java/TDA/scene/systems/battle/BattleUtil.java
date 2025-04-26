package TDA.scene.systems.battle;

import TDA.entities.dinos.Dino;

import java.util.*;

public class BattleUtil {
    private static final Random random = new Random();
    private static BattleContext context;

    public static void setContext(BattleContext context) {
        BattleUtil.context = context;
    }

    /**
     * Returns the team of which the dino is a part of
     *
     * @param dino the dino to check for
     * @return the team of the given dino
     */
    public static Team getAlliedTeam(Dino dino) {
        return Arrays.asList(context.alliedTeam.getDinos()).contains(dino) ? context.alliedTeam : context.enemyTeam;
    }

    /**
     * Returns the enemy team of the given dino
     *
     * @param dino the dino to check for
     * @return the enemy team of the given dino
     */
    public static Team getEnemyTeam(Dino dino) {
        return Arrays.asList(context.alliedTeam.getDinos()).contains(dino) ? context.enemyTeam : context.alliedTeam;
    }

    public static Dino getRandomTarget(Dino caster, boolean isEnemy) {
        Team targetTeam = isEnemy ? getEnemyTeam(caster) : getAlliedTeam(caster);
        Dino[] targetDinos = targetTeam.getDinos();
        return targetDinos[random.nextInt(targetDinos.length)];
    }

    public static List<Dino> getRandomTargets(Dino caster, boolean isEnemy, int amount) {
        List<Dino> availableTargets = new ArrayList<>(Arrays.asList(isEnemy ? getEnemyTeam(caster).getDinos() : getAlliedTeam(caster).getDinos()));
        Collections.shuffle(availableTargets, random);

        List<Dino> selectedTargets = new ArrayList<>();
        int actualAmount = Math.min(amount, availableTargets.size());
        for (int i = 0; i < actualAmount; i++) {
            Dino target = availableTargets.get(i);
            selectedTargets.add(target);
        }
        return selectedTargets;
    }
}
