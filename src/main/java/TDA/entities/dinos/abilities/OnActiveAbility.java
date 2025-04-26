package TDA.entities.dinos.abilities;

import TDA.entities.dinos.Dino;
import TDA.scene.systems.battle.BattleContext;

public interface OnActiveAbility {
    void activate(Dino caster, BattleContext context);
    float getDuration();
}
