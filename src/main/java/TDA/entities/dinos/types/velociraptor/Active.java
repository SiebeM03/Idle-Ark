package TDA.entities.dinos.types.velociraptor;

import TDA.entities.dinos.Dino;
import TDA.entities.dinos.abilities.Ability;
import TDA.entities.dinos.abilities.OnActiveAbility;
import TDA.scene.systems.battle.BattleContext;
import TDA.scene.systems.battle.BattleUtil;
import TDA.scene.systems.battle.DamageEvent;

public class Active extends Ability implements OnActiveAbility {

    public Active() {
        super("Pack Ambush", "Leaps at the enemy with the lowest HP, dealing high single-target damage and applying a bleed effect for 3 rounds.", 1f);
    }

    @Override
    public void activate(Dino caster, BattleContext context) {
        Dino[] enemies = BattleUtil.getEnemyTeam(caster).getDinos();

        Dino lowestEnemy = null;
        for (Dino enemy : enemies) {
            if (lowestEnemy == null || enemy.battleHandler().getCurrentHealth() < lowestEnemy.battleHandler().getCurrentHealth()) {
                lowestEnemy = enemy;
            }
        }
        if (lowestEnemy == null) return;
        lowestEnemy.battleHandler().takeDamage(new DamageEvent(caster, (int) (caster.getStats().damageStat.getActualValue() * 3)), context);
    }
}
