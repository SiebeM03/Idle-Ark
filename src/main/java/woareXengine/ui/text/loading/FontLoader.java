package woareXengine.ui.text.loading;

import woareXengine.openglWrapper.textures.Texture;
import woareXengine.ui.text.basics.Font;
import woareXengine.util.Assets;

import java.io.File;

import static org.lwjgl.opengl.GL11.GL_LINEAR;

public class FontLoader {
    public static Font load(String filepath) {
        File fontMetaFile = new File(filepath);
        TextGenerator generator = new MetaDataLoader(fontMetaFile).loadMetaData();
        Texture texture = Assets.getTexture(generator.textureFile.getPath());
        texture.setFilters(GL_LINEAR, GL_LINEAR);
        return new Font(texture, generator);
    }
}
