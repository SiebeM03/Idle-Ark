package TDA.entities.main;

public abstract class Component {
    protected Entity entity;

    /**
     * Called after the component is added to an entity
     */
    public void init() {
    }

    /**
     * Called every frame
     */
    public void update() {
    }

    /**
     * Called when the entity is destroyed
     */
    public void destroy() {
    }
}
