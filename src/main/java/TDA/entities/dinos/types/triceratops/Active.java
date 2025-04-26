package TDA.entities.dinos.types.triceratops;

import TDA.entities.dinos.Dino;
import TDA.entities.dinos.abilities.Ability;
import TDA.entities.dinos.abilities.OnActiveAbility;
import TDA.scene.systems.battle.BattleContext;

public class Active extends Ability implements OnActiveAbility {
    public Active() {
        super("Shield Charge", "Rushes into an enemy, dealing damage and stunning them for 2 rounds", 1f);
    }

    @Override
    public void activate(Dino caster, BattleContext context) {

    }
}
