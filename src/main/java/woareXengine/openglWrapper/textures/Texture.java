package woareXengine.openglWrapper.textures;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.GL_RGB32F;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Texture {
    public final String filepath;
    public int width;
    public int height;

    public int textureID;

    public Texture(String filepath) {
        this.width = 100;
        this.height = 100;
        this.filepath = filepath;
        init();
    }

    /** Only used by {@link woareXengine.openglWrapper.framebuffer.Framebuffer Framebuffer} to attach a Texture. */
    public Texture(int width, int height) {
        this.filepath = "Generated_Texture";

        textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);

        setFilters(GL_LINEAR, GL_LINEAR);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB32F, width, height, 0, GL_RGB, GL_FLOAT, NULL);
    }

    public Texture(String filepath, int width, int height) {
        this.width = width;
        this.height = height;
        this.filepath = filepath;
        init();
    }

    public void init() {
        textureID = glGenTextures();
        bind();

        setClampedEdges(false);

        setFilters(GL_NEAREST, GL_NEAREST);

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        stbi_set_flip_vertically_on_load(true);
        ByteBuffer image = stbi_load(filepath, width, height, channels, 0);

        if (image == null) {
            assert false : "Error: (Texture) Could not load image '" + filepath + "'";
        }

        this.width = width.get(0);
        this.height = height.get(0);

        if (channels.get(0) == 3) {
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width.get(0), height.get(0), 0, GL_RGB, GL_UNSIGNED_BYTE, image);
        } else if (channels.get(0) == 4) {
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
        } else {
            assert false : "Error: (Texture) Unknown number of channels '" + channels.get(0) + "'";
        }

        stbi_image_free(image);
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, textureID);
    }

    public void bindToSlot(int slot) {
        glActiveTexture(GL_TEXTURE0 + slot);
        glBindTexture(GL_TEXTURE_2D, textureID);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }


    public static Texture getTexture(String filepath) {
        return new Texture(filepath);
    }


    // =============================================== Parameter setters ===============================================
    public void setFilters(int minFilterType, int magFilterType) {
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, minFilterType);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, magFilterType);
    }

    /**
     * Set the texture to repeat or clamp to edge.
     *
     * @param clamp True to clamp to edge, false to repeat.
     */
    public void setClampedEdges(boolean clamp) {
        int wrapType = clamp ? GL_CLAMP_TO_EDGE : GL_REPEAT;
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, wrapType);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, wrapType);
    }
}
