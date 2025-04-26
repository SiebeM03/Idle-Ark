package TDA.entities.flag;

import TDA.entities.components.rendering.QuadComp;
import TDA.entities.dinos.Dino;
import TDA.entities.main.Entity;
import org.joml.Vector2f;
import woareXengine.util.Assets;
import woareXengine.util.Layer;
import woareXengine.util.Transform;

public class Flag {
    public static Entity create(float x, float y, Dino... enemyDinos) {
        return new Entity(new Transform(new Vector2f(x, y), new Vector2f(200, 200)))
                       .addComponent(new QuadComp(Assets.getTexture("src/assets/images/seperateImages/flag.png"), Layer.FOREGROUND))
                       .addComponent(new EnemyTeamComp(enemyDinos));
    }
}
