package TDA.entities.dinos;

import TDA.entities.components.rendering.QuadComp;
import TDA.entities.dinos.abilities.Ability;
import TDA.entities.dinos.abilities.AbilityHandler;
import TDA.entities.dinos.stats.StatsComp;
import TDA.entities.main.Component;
import TDA.entities.main.Entity;
import woareXengine.util.Transform;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class Dino extends Entity {
    private final AbilityHandler abilityHandler;

    private StatsComp statsComp;
    private BattleHandler battleHandler;

    public Dino(Transform transform, Ability activeAbility, Ability passiveAbility, Ability statsAbility, QuadComp quadComp, Component... components) {
        super(transform, components);

        addComponent(quadComp);

        List<Ability> abilities = Stream.of(activeAbility, passiveAbility, statsAbility).filter(Objects::nonNull).toList();
        abilityHandler = new AbilityHandler(this, abilities);
    }

    public StatsComp getStats() {
        if (statsComp == null) {
            return getComponent(StatsComp.class);
        }
        return statsComp;
    }

    public AbilityHandler abilityHandler() {
        return abilityHandler;
    }

    public BattleHandler battleHandler() {
        return battleHandler;
    }

    public void setBattleHandler(BattleHandler battleHandler) {
        this.battleHandler = battleHandler;
    }

    public Dino withStats(int healthPoints, int damagePoints, int speedPoints) {
        if (getStats() == null) {
            statsComp = new StatsComp(healthPoints, damagePoints, speedPoints);
            addComponent(statsComp);
        } else {
            statsComp.healthStat.setPoints(healthPoints);
            statsComp.damageStat.setPoints(damagePoints);
            statsComp.speedStat.setPoints(speedPoints);
        }
        initActualStatCalculations();
        return this;
    }

    /**
     * Initialize the actual stat calculations for the Dino, only initialize the calculations that are unique for each species of Dino.
     */
    protected abstract void initActualStatCalculations();

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{ " +
                       getStats().healthStat + ", " +
                       getStats().damageStat + ", " +
                       getStats().speedStat + ", " +
                       getStats().critStat + ", " +
                       getStats().critDamageStat +
                       "}";
    }

    @Override
    public void update() {
        super.update();
        if (battleHandler != null) {
            battleHandler().update();
        }
    }
}
