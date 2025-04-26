package TDA.entities.dinos.stats.types;

import TDA.entities.dinos.stats.ActualStatCalculator;
import TDA.entities.dinos.stats.Stat;

public class CritStat extends Stat {

    public CritStat(int points) {
        super(points);
        setMaxPoints(200);
        this.name = "CRT";
    }

    @Override
    protected void init() {
        actualStatCalculator = points -> (float) points / getMaxPoints();
    }

    @Override
    public void setActualStatCalculator(ActualStatCalculator actualStatCalculator) {
        throw new UnsupportedOperationException("CritStat does not support custom calculators");
    }
}
