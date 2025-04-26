package TDA.entities.dinos.abilities;

import TDA.entities.dinos.Dino;
import TDA.scene.systems.battle.BattleContext;
import TDA.scene.systems.battle.DamageEvent;

import java.util.ArrayList;
import java.util.List;

public class AbilityHandler {
    private final Dino dino;
    private final List<Ability> abilities;

    public AbilityHandler(Dino dino, List<Ability> abilities) {
        this.dino = dino;
        this.abilities = abilities;

        for (Ability ability : abilities) {
            ability.setDino(dino);
        }
    }


    /**
     * Called when the battle starts, typically used to apply any effects that should be active at the start of the battle (e.g. stat buffs)
     */
    public void onBattleStart(BattleContext context) {
        for (OnBattleStartAbility ability : getAbilities(OnBattleStartAbility.class)) {
            ability.onBattleStart(dino, context);
        }
    }

    /**
     * Called when the battle ends, typically used to revert any effects that were applied at the start of the battle
     */
    public void onBattleEnd(BattleContext context) {
        for (OnBattleStartAbility ability : getAbilities(OnBattleStartAbility.class)) {
            ability.onBattleEnd(dino, context);
        }
    }

    /**
     * Called when the dino is ready to launch its active ability (e.g. when the dino's turn comes up in the battle)
     */
    public float onActive(BattleContext context) {
        float duration = 0;
        for (OnActiveAbility ability : getAbilities(OnActiveAbility.class)) {
            ability.activate(dino, context);
            duration = Math.max(duration, ability.getDuration());
        }
        return duration;
    }

    /**
     * Called when the dino takes damage, typically used for abilities that trigger when the dino takes damage (e.g. a shield that absorbs damage, counter-attacks, etc.)
     */
    public void onDamage(BattleContext context, DamageEvent damageEvent) {
        for (OnDamageAbility ability : getAbilities(OnDamageAbility.class)) {
            ability.onDamageTaken(dino, damageEvent, context);
        }
    }

    /**
     * Called when the dino dies, typically used for abilities that trigger when the dino dies (e.g. a self-destruct ability)
     */
    public void onDeath(BattleContext context) {
        for (OnDeathAbility ability : getAbilities(OnDeathAbility.class)) {
            ability.onDeath(dino, context);
        }
    }


    private <T> List<T> getAbilities(Class<T> type) {
        List<T> results = new ArrayList<>();
        for (Ability ability : abilities) {
            if (type.isAssignableFrom(ability.getClass())) {
                results.add(type.cast(ability));
            }
        }
        return results;
    }
}
