package TDA.entities.dinos.stats.types;

import TDA.entities.dinos.stats.ActualStatCalculator;
import TDA.entities.dinos.stats.Stat;

public class CritDamageStat extends Stat {

    public CritDamageStat(int points) {
        super(points);
        this.name = "CRT DMG";
    }

    @Override
    protected void init() {
        actualStatCalculator = points -> points / 2f;
    }

    @Override
    public void setActualStatCalculator(ActualStatCalculator actualStatCalculator) {
        throw new UnsupportedOperationException("CritDamageStat does not support custom calculators");
    }
}
