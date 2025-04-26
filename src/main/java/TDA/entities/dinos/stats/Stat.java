package TDA.entities.dinos.stats;

/**
 * Represents a stat of a Dino, containing the points and a method to calculate the actual value of the stat based on the points
 */
public abstract class Stat {
    protected String name;
    private int maxPoints;
    protected int points;
    protected ActualStatCalculator actualStatCalculator;

    public Stat(int points) {
        this.points = points;
        init();
    }

    /**
     * Initializes the stat with the default actualStatCalculator
     */
    protected void init() {
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = Math.min(maxPoints, points);
    }

    protected void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
        setPoints(points);  // Ensure points are within the new maxPoints
    }
    protected int getMaxPoints() {
        return maxPoints;
    }


    public double getActualValue() {
        return actualStatCalculator.calculateActualStat(points);
    }

    public void setActualStatCalculator(ActualStatCalculator actualStatCalculator) {
        this.actualStatCalculator = actualStatCalculator;
    }

    @Override
    public String toString() {
        return name + "={" + points + "->" + String.format("%,.2f", getActualValue()) + "}";
    }
}
