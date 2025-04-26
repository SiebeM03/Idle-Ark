package TDA.scene;

import TDA.entities.components.rendering.QuadComp;
import TDA.entities.main.Component;
import TDA.entities.main.Entity;
import TDA.entities.main.EntityManager;
import TDA.entities.components.interactions.ClickableComp;
import TDA.main.GameManager;
import TDA.rendering.SceneRenderSystem;
import TDA.rendering.TDARenderEngine.renderSystem.TDARenderSystem;
import TDA.ui.TDAUi;
import woareXengine.io.userInputs.Input;
import woareXengine.mainEngine.Engine;
import woareXengine.mainEngine.gameObjects.Camera;
import woareXengine.util.Id;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class Scene {

    public final Camera camera;

    public final SceneRenderSystem renderer;
    public final EntityManager entityManager;

    private Map<Id, SceneSystem> sceneSystems = new LinkedHashMap<>();

    protected Scene(SceneRenderSystem renderer, Camera camera) {
        this.renderer = renderer;
        this.camera = camera;
        this.entityManager = new EntityManager();
    }

    public void update() {
        for (SceneSystem system : sceneSystems.values()) {
            system.update();
        }

        entityManager.update();
    }

    public void addEntity(Entity entity) {
        entity.scene = this;
        entityManager.entities.add(entity);
        if (entity.getComponent(QuadComp.class) != null) {
            renderer.registerQuad(entity.getComponent(QuadComp.class).quad);
        }
    }

    public void addSystem(SceneSystem system) {
        sceneSystems.put(system.getId(), system);
    }

    public SceneSystem getSystem(Id id) {
        return sceneSystems.get(id);
    }

    public void render() {
        // TODO add pre-render updates for SceneSystem if needed
        renderer.render(this);
    }

    public void cleanUp() {
        for (SceneSystem system : sceneSystems.values()) {
            system.cleanUp();
        }
        renderer.cleanUp();
    }

    /** Clears the renderer data */
    public void clearRenderer() {
        renderer.clear();
    }

    @SafeVarargs
    public final List<Entity> getEntitiesWithComponents(Class<? extends Component>... component) {
        List<Entity> entities = new ArrayList<>();
        for (Entity entity : entityManager.entities) {
            boolean hasAllComponents = true;
            for (Class<? extends Component> c : component) {
                if (entity.getComponent(c) == null) {
                    hasAllComponents = false;
                    break;
                }
            }
            if (hasAllComponents) {
                entities.add(entity);
            }
        }
        return entities;
    }

    public Entity getClickableEntityAtMouse() {
        int pixelId = GameManager.currentScene.renderer.getPickingRenderer().readPixel(
                Input.mouse().getScreenX(),
                Input.mouse().getScreenY()
        );
        return getEntitiesWithComponents(ClickableComp.class)
                       .stream()
                       .filter(e -> e.getId() == pixelId)
                       .findFirst()
                       .orElse(null);
    }

    public void fill() {
        addSystems();
        createEntities();
    }

    protected abstract void createEntities();

    protected abstract void addSystems();

    public void close() {

    }
}
