package TDA.entities.dinos.stats;

import TDA.entities.dinos.Dino;
import TDA.entities.dinos.stats.types.*;
import TDA.entities.main.Component;

public class StatsComp extends Component {
    public final Stat healthStat;
    public final Stat damageStat;
    public final Stat speedStat;
    public final Stat critStat;
    public final Stat critDamageStat;

    public StatsComp(int healthPoints, int damagePoints, int speedPoints) {
        this.healthStat = new HealthStat(healthPoints);
        this.damageStat = new DamageStat(damagePoints);
        this.speedStat = new SpeedStat(speedPoints);
        this.critStat = new CritStat(130);
        this.critDamageStat = new CritDamageStat(400);
    }

    @Override
    public void init() {
        if (!(entity instanceof Dino)) {
            throw new IllegalArgumentException("Stats Component must be attached to a Dino entity");
        }
    }
}
