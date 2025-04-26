package TDA.entities.dinos.types.giga;

import TDA.entities.dinos.Dino;
import TDA.entities.dinos.abilities.Ability;
import TDA.entities.dinos.abilities.OnActiveAbility;
import TDA.scene.systems.battle.BattleContext;
import TDA.scene.systems.battle.BattleUtil;
import TDA.scene.systems.battle.DamageEvent;

import java.util.List;

public class Active extends Ability implements OnActiveAbility {

    public Active() {
        super("Giga Roar", "Increases damage by 30% for 3 rounds, decreases by 10% each round", 2.0f);
    }

    @Override
    public void activate(Dino caster, BattleContext context) {
        List<Dino> targets = BattleUtil.getRandomTargets(caster, true, 2);
        for (Dino target : targets) {
            target.battleHandler().takeDamage(new DamageEvent(caster, 5000, false), context);
        }
    }
}
