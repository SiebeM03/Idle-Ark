package TDA.entities.dinos.types.velociraptor;

import TDA.entities.components.rendering.QuadComp;
import TDA.entities.dinos.Dino;
import org.joml.Vector2f;
import woareXengine.util.Assets;
import woareXengine.util.Layer;
import woareXengine.util.Transform;

public class Velociraptor extends Dino {
    public Velociraptor() {
        super(new Transform(new Vector2f(), new Vector2f(207, 145)),
                new Active(),
                new Passive(),
                null,
                new QuadComp(Assets.getTexture("src/assets/images/seperateImages/dinos/velociraptor.png"), Layer.FOREGROUND)
        );
        this.getComponent(QuadComp.class).quad.horizontalFlip();
    }

    @Override
    protected void initActualStatCalculations() {
        getStats().healthStat.setActualStatCalculator(points -> {
            int base = 1500;
            return base + 180 * points + 10000 * points * (points / (points + 5000f));
        });

        getStats().damageStat.setActualStatCalculator(points -> {
            int base = 140;
            return base + 14 * points + 1000 * points * (points / (points + 5000f));
        });
    }
}
