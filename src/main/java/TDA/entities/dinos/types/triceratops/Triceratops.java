package TDA.entities.dinos.types.triceratops;

import TDA.entities.components.rendering.QuadComp;
import TDA.entities.dinos.Dino;
import org.joml.Vector2f;
import woareXengine.util.Assets;
import woareXengine.util.Layer;
import woareXengine.util.Transform;

public class Triceratops extends Dino {

    public Triceratops() {
        super(new Transform(new Vector2f(), new Vector2f(240, 133)),
                null,
                null,
                null,
                new QuadComp(Assets.getTexture("src/assets/images/seperateImages/dinos/triceratops.png"), Layer.FOREGROUND)
        );
        this.getComponent(QuadComp.class).quad.horizontalFlip();
    }
    @Override
    protected void initActualStatCalculations() {
        getStats().healthStat.setActualStatCalculator(points -> {
            int base = 1000;
            return base + 100 * points + 10000 * points * (points / (points + 5000f));
        });

        getStats().damageStat.setActualStatCalculator(points -> {
            int base = 200;
            return base + 30 * points + 1000 * points * (points / (points + 5000f));
        });
    }
}
