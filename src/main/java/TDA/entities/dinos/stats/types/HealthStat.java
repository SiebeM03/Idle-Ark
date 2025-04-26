package TDA.entities.dinos.stats.types;

import TDA.entities.dinos.stats.Stat;

public class HealthStat extends Stat {

    public HealthStat(int points) {
        super(points);
        this.name = "HP";
    }
}
