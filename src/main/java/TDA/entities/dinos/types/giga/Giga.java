package TDA.entities.dinos.types.giga;

import TDA.entities.components.rendering.QuadComp;
import TDA.entities.dinos.Dino;
import org.joml.Vector2f;
import woareXengine.util.Assets;
import woareXengine.util.Layer;
import woareXengine.util.Transform;

public class Giga extends Dino {
    public Giga() {
        super(new Transform(new Vector2f(), new Vector2f(256, 144)),
                new Active(),
                null,
                null,
                new QuadComp(Assets.getTexture("src/assets/images/seperateImages/dinos/giga.png"), Layer.FOREGROUND)
        );
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
