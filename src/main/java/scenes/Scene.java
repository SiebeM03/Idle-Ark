package scenes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import engine.ecs.Component;
import engine.ecs.GameObject;
import engine.ecs.serialization.*;
import engine.graphics.Camera;
import engine.graphics.renderer.Renderer;
import game.GameManager;
import game.resources.Resource;
import imgui.ImGui;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

    protected Renderer renderer = new Renderer();
    protected Camera camera;
    private boolean isRunning = false;
    protected List<GameObject> gameObjects = new ArrayList<>();

    protected GameObject activeGameObject = null;
    /**
     * A boolean that shows whether GameObjects have been read from a file. This can be used to prevent scenes from initializing GameObjects that have already been created by reading the file.
     */
    protected boolean levelLoaded = false;

    public Scene() {

    }

    public void init() {

    }

    public void start() {
        for (GameObject go : gameObjects) {
            go.start();
            this.renderer.add(go);
        }
        isRunning = true;
    }

    public void addGameObjectToScene(GameObject go) {
        if (!isRunning) {
            gameObjects.add(go);
        } else {
            gameObjects.add(go);
            go.start();
            this.renderer.add(go);
        }
    }

    public abstract void update(float dt);
    public abstract void render();

    public Camera camera() {
        return this.camera;
    }

    public void sceneImgui() {
        if (activeGameObject != null) {
            ImGui.begin("Inspector");
            activeGameObject.imgui();
            ImGui.end();
        }

        imgui();
    }

    public void imgui() {

    }

    public void saveExit() {
        Gson gson = new GsonBuilder()
                            .setPrettyPrinting()
                            .registerTypeAdapter(Component.class, new ComponentSerializer())
                            .registerTypeAdapter(GameObject.class, new GameObjectSerializer())
                            .registerTypeAdapter(Resource.class, new ResourceSerializer())
                            .create();

        try {
            FileWriter writer = new FileWriter("level.txt");
            writer.write(gson.toJson(this.gameObjects));
            writer.close();

            List<Resource> resources = GameManager.get().getResourceManager().getResources();
            writer = new FileWriter("data.txt");
            writer.write(gson.toJson(new Data(resources)));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        Gson gson = new GsonBuilder()
                            .setPrettyPrinting()
                            .registerTypeAdapter(Component.class, new ComponentSerializer())
                            .registerTypeAdapter(GameObject.class, new GameObjectSerializer())
                            .registerTypeAdapter(Resource.class, new ResourceSerializer())
                            .create();

        loadGameObjectsAndComponents(gson);
        loadGameData(gson);
    }

    /**
     * <ol>
     * <li>Loads all GameObjects and Components from level.txt</li>
     * <li>Adds all Components to the corresponding GameObject</li>
     * <li>Adds all GameObjects to the scene</li>
     * </ol>
     *
     * @param gson GsonBuilder that has registered a GameObject and Component type adapter
     * @return true if the file was read, false if the file was empty.
     */
    private boolean loadGameObjectsAndComponents(Gson gson) {
        String levelFile = "";

        try {
            levelFile = new String(Files.readAllBytes(Paths.get("level.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!levelFile.equals("")) {
            int maxGoId = -1;
            int maxCompId = -1;
            GameObject[] objs = gson.fromJson(levelFile, GameObject[].class);
            for (GameObject go : objs) {
                addGameObjectToScene(go);

                for (Component c : go.getAllComponents()) {
                    if (c.getUid() > maxCompId) {
                        maxCompId = c.getUid();
                    }
                }
                if (go.getUid() > maxGoId) {
                    maxGoId = go.getUid();
                }
            }

            // Update the ID_COUNTER values for GameObject and Component
            maxGoId++;
            maxCompId++;
            GameObject.init(maxGoId);
            Component.init(maxCompId);

            this.levelLoaded = true;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Loads all game data from data.txt and initializes GameManager with the loaded data.
     *
     * @param gson GsonBuilder that has registered a Resource type adapter
     * @return true if the file was read, false if the file was empty.
     */
    private boolean loadGameData(Gson gson) {
        String dataFile = "";
        try {
            dataFile = new String(Files.readAllBytes(Paths.get("data.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initialize GameManager instance
        GameManager gameManager = GameManager.get();

        if (!dataFile.equals("")) {
            // Load Data record
            Data data = gson.fromJson(dataFile, Data.class);
            // Initialize ResourceManager and pass the loaded Resource objects to it
            gameManager.initResourceManager();
            gameManager.getResourceManager().setResources(data.resources());
            return true;
        } else {
            // Initialize ResourceManager and keep the default Resource objects
            gameManager.initResourceManager();
            return false;
        }
    }
}
