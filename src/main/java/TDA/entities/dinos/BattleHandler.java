package TDA.entities.dinos;

import TDA.entities.components.rendering.QuadComp;
import TDA.scene.systems.battle.BattleContext;
import TDA.scene.systems.battle.DamageEvent;
import woareXengine.util.Color;
import woareXengine.util.Delay;

public class BattleHandler {
    private Dino dino;

    private double health;
    private double damage;
    private double speed;

    private double currentHealth;

    private Delay damageTakenColorStartDelay = new Delay(0.3f);
    private Delay damageTakenColorStopDelay = new Delay(0.6f);

    public BattleHandler(Dino dino) {
        this.dino = dino;

        this.health = dino.getStats().healthStat.getActualValue();
        this.damage = dino.getStats().damageStat.getActualValue();
        this.speed = dino.getStats().speedStat.getActualValue();

        this.currentHealth = this.health;

        damageTakenColorStartDelay.stop();
        damageTakenColorStopDelay.stop();
    }

    public boolean isAlive() {
        return currentHealth > 0;
    }

    public void takeDamage(DamageEvent damageEvent, BattleContext context) {
        currentHealth -= damageEvent.damage();
        damageTakenColorStartDelay.reset().start();

        context.updateHealthBar(dino);

        if (!isAlive()) {
            dino.abilityHandler().onDeath(context);
            context.handleDeath(dino);
            return;
        }

        if (damageEvent.canTriggerOnDamage()) {
            dino.abilityHandler().onDamage(context, damageEvent);
        }
    }

    public void startBattle(BattleContext context) {
        dino.abilityHandler().onBattleStart(context);
        this.currentHealth = this.health;
    }

    public void finishBattle(BattleContext context) {
        dino.abilityHandler().onBattleEnd(context);
    }

    public float getHealthPercentage() {
        return (float) (currentHealth / health);
    }

    public float getCurrentHealth() {
        return (float) currentHealth;
    }

    public void update() {
        if (damageTakenColorStartDelay.isOver()) {
            dino.getComponent(QuadComp.class).quad.color = Color.RED;
            damageTakenColorStartDelay.reset().stop();
            damageTakenColorStopDelay.reset().start();
        }

        if (damageTakenColorStopDelay.isOver()) {
            dino.getComponent(QuadComp.class).quad.color = Color.WHITE;
            damageTakenColorStopDelay.reset().stop();
        }
    }
}
