package TDA.entities.dinos.types.rex;

import TDA.entities.dinos.Dino;
import TDA.entities.dinos.abilities.Ability;
import TDA.entities.dinos.abilities.OnActiveAbility;
import TDA.scene.systems.battle.BattleContext;
import TDA.scene.systems.battle.BattleUtil;
import TDA.scene.systems.battle.DamageEvent;

public class Active extends Ability implements OnActiveAbility {

    public Active() {
        super("Savage Roar", "Increases damage by 30% for 3 rounds, decreases by 10% each round", 2f);
    }

    @Override
    public void activate(Dino caster, BattleContext context) {
        Dino target = BattleUtil.getRandomTarget(caster, true);

        target.battleHandler().takeDamage(new DamageEvent(caster, 1000), context);
    }
}
