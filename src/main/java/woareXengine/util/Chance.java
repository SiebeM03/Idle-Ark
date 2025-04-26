package woareXengine.util;

public class Chance {
    /**
     * Returns a random boolean based on the chance.
     *
     * @param chance the chance of returning true
     * @return a random boolean based on the chance
     */
    public static boolean chance(float chance) {
        return Math.random() < chance;
    }
}
