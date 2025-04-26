package TDA.entities.dinos.abilities;

import TDA.entities.dinos.Dino;
import TDA.scene.systems.battle.BattleContext;

public interface PassiveAbility {
    void trigger(Dino caster, BattleContext context);
}
