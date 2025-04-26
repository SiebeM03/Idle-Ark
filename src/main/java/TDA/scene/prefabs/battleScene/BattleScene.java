package TDA.scene.prefabs.battleScene;

import TDA.entities.dinos.Dino;
import TDA.rendering.TDARenderEngine.renderSystem.TDARenderSystem;
import TDA.scene.Scene;
import TDA.scene.systems.battle.BattleConfigs;
import TDA.scene.systems.battle.BattleContext;
import org.joml.Vector2f;
import woareXengine.mainEngine.Engine;
import woareXengine.openglWrapper.textures.Texture;
import woareXengine.rendering.quadRenderer.Quad;
import woareXengine.util.Assets;
import woareXengine.util.Color;
import woareXengine.util.Layer;
import woareXengine.util.Logger;

public class BattleScene extends Scene {
    private final BattleContext context;

    public BattleScene(BattleContext context) {
        super(new TDARenderSystem(), new BattleCamera());
        this.context = context;

        Logger.success("Battle Scene initialized");
    }

    @Override
    protected void createEntities() {
        Logger.info("Creating entities");
        Texture backgroundTexture = Assets.getTexture("src/assets/images/seperateImages/battleBackground.png");
        float scale = Math.max((float) Engine.window().getPixelWidth() / backgroundTexture.width, (float) Engine.window().getPixelHeight() / backgroundTexture.height);
        Quad backgroundQuad = new Quad(backgroundTexture.width * scale, backgroundTexture.height * scale, new Vector2f(), Layer.BACKGROUND);
        backgroundQuad.texture = backgroundTexture;
        renderer.registerQuad(backgroundQuad);

        for (Dino dino : context.orderedDinosList()) {
            addEntity(dino);
            System.out.print("\t");
            System.out.println("Added dino at " + dino.transform.getX() + ", " + dino.transform.getY());
        }
    }

    @Override
    protected void addSystems() {
        addSystem(context);
    }

    @Override
    public void close() {
        context.cleanUp();
    }
}
