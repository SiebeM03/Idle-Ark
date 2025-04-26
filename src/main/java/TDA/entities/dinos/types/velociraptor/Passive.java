package TDA.entities.dinos.types.velociraptor;

import TDA.entities.dinos.Dino;
import TDA.entities.dinos.abilities.Ability;
import TDA.entities.dinos.abilities.PassiveAbility;
import TDA.scene.systems.battle.BattleContext;
import TDA.scene.systems.battle.BattleUtil;
import TDA.scene.systems.battle.DamageEvent;

public class Passive extends Ability implements PassiveAbility {

    public Passive() {
        super("Pack Hunter", "When an active ability is launched, all allied Velociraptors attack a random enemy that has a blood effect dealing 50% of the damage.", 1f);
    }

    @Override
    public void trigger(Dino caster, BattleContext context) {
        Dino[] allies = BattleUtil.getAlliedTeam(caster).getDinos();
        for (Dino ally : allies) {
            if (ally == caster) continue;
            if (ally.getClass().isAssignableFrom(Velociraptor.class)) {
                Dino target = BattleUtil.getRandomTarget(ally, true);
                target.battleHandler().takeDamage(new DamageEvent(ally, (int) (ally.getStats().damageStat.getActualValue() * 0.5)), context);
            }
        }
    }
}
