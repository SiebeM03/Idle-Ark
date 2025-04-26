package woareXengine.util;

import woareXengine.mainEngine.Engine;

public class Delay {
    private final float duration;
    private float timePassed = 0;

    private boolean isPaused = false;

    public Delay(float duration) {
        this.duration = duration;
    }

    /** Update the delay and return whether it is over. */
    public boolean isOver() {
        update();

        if (timePassed >= duration) {
            stop();
            return true;
        } else {
            return false;
        }
    }

    public float timeRemaining() {
        return duration - timePassed;
    }

    public void update() {
        if (isPaused) return;
        timePassed += Engine.getDelta();
    }

    public Delay reset() {
        timePassed = 0;
        return this;
    }

    public void stop() {
        isPaused = true;
    }

    public void start() {
        isPaused = false;
    }
}
