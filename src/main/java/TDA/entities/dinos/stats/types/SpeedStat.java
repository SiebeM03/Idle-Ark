package TDA.entities.dinos.stats.types;

import TDA.entities.dinos.stats.ActualStatCalculator;
import TDA.entities.dinos.stats.Stat;

public class SpeedStat extends Stat {

    public SpeedStat(int points) {
        super(points);
        this.name = "SPD";
    }

    @Override
    protected void init() {
        actualStatCalculator = points -> 100 + points;
    }

    @Override
    public void setActualStatCalculator(ActualStatCalculator actualStatCalculator) {
        throw new UnsupportedOperationException("SpeedStat does not support custom calculators");
    }
}
