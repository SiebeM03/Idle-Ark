package TDA.entities.dinos.abilities;

import TDA.entities.dinos.Dino;

public abstract class Ability {
    protected final String name;
    protected final String description;
    protected final float duration;
    protected Dino dino;

    public Ability(String name, String description, float duration) {
        this.name = name;
        this.description = description;
        this.duration = duration;
    }

    public void setDino(Dino dino) {
        this.dino = dino;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getDuration() {
        return duration;
    }
}
