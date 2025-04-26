package woareXengine.rendering;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import woareXengine.openglWrapper.textures.Texture;
import woareXengine.rendering.renderData.Primitive;
import woareXengine.rendering.renderData.ShaderDataType;
import woareXengine.util.Color;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class RenderBatch implements Comparable<RenderBatch> {
    /** Number of attributes (int or float, e.g., positions, colors, UVs) per vertex. */
    private int attributesPerVertex;
    /** Size of a single vertex in bytes. */
    private int bytesPerVertex;

    /** Array of shader data types that define the structure of a vertex. */
    private final ShaderDataType[] attributes;
    /** Type of primitive this batch will render (e.g., TRIANGLES, QUADS). */
    public final Primitive primitive;

    /** Buffer for storing vertex data to be sent to the GPU. */
    public float[] vertexArray;

    private List<Texture> boundTextures;

    private int vaoID, vboID, eboID;
    private int maxBatchSize;
    private int zIndex;

    private int currentVertexArrayIndex;


    public RenderBatch(int maxBatchSize, int zIndex, Primitive primitive, ShaderDataType... attributes) {
        this.maxBatchSize = maxBatchSize;
        this.zIndex = zIndex;
        this.primitive = primitive;
        this.attributes = attributes;

        for (ShaderDataType s : attributes) {
            attributesPerVertex += s.count;
            bytesPerVertex += s.sizeInBytes;
        }
        vertexArray = new float[maxBatchSize * primitive.vertexCount * attributesPerVertex];

        this.currentVertexArrayIndex = 0;
        this.boundTextures = new ArrayList<>();
    }

    public void init() {
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, (long) maxBatchSize * primitive.vertexCount * bytesPerVertex, GL_DYNAMIC_DRAW);

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, generateIndices(), GL_STATIC_DRAW);

        int currentOffset = 0;
        for (int i = 0; i < attributes.length; i++) {
            ShaderDataType attribute = attributes[i];
            glVertexAttribPointer(i, attribute.count, attribute.openglType, false, bytesPerVertex, currentOffset);
            glEnableVertexAttribArray(i);
            currentOffset += attribute.sizeInBytes;
        }
    }

    public void bind() {
        glBindVertexArray(vaoID);
        for (int i = 0; i < boundTextures.size(); i++) {
            boundTextures.get(i).bindToSlot(i + 1);
        }
    }

    public void unbind() {
        for (Texture texture : boundTextures) {
            texture.unbind();
        }
        glBindVertexArray(0);
    }

    public void start() {
        currentVertexArrayIndex = 0;
        boundTextures.clear();
    }

    public void finish() {
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertexArray);
    }

    public int addTexture(Texture texture) {
        if (texture == null) return 0;
        if (!boundTextures.contains(texture)) {
            boundTextures.add(texture);
        }
        return boundTextures.indexOf(texture) + 1;
    }


    public int zIndex() {
        return zIndex;
    }

    public boolean hasVertexRoom() {
        return currentVertexArrayIndex < vertexArray.length;
    }

    public boolean hasTextureRoom() {
        return boundTextures.size() < 8;
    }

    public boolean hasTextureRoom(Texture texture) {
        return texture == null  // Null texture is always allowed
                       || boundTextures.size() < 8  // Max 8 textures
                       || boundTextures.contains(texture); // Texture already bound
    }

    /**
     * Get the number of vertices to be drawn
     *
     * @return the number of vertices to be drawn
     */
    public int getVertexCount() {
        // Safety check (currentVertexArrayIndex should be a multiple of attributesPerVertex, if not, not all vertices are complete)
        if (currentVertexArrayIndex % attributesPerVertex != 0) {
            assert false : "A renderer seems to not have the correct amount of vertices";
        }
        return (currentVertexArrayIndex * primitive.elementCount) / (attributesPerVertex * primitive.vertexCount);
    }


    private int[] generateIndices() {
        int[] elements = new int[primitive.elementCount * maxBatchSize];
        for (int i = 0; i < maxBatchSize; i++) {
            primitive.elementCreation.accept(elements, i);
        }
        return elements;
    }


    @Override
    public int compareTo(@NotNull RenderBatch o) {
        return Integer.compare(this.zIndex, o.zIndex);
    }


    public void pushFloat(float f) {
        vertexArray[currentVertexArrayIndex++] = f;
    }

    public void pushInt(int i) {
        vertexArray[currentVertexArrayIndex++] = i;
    }

    public void pushVec2(float x, float y) {
        vertexArray[currentVertexArrayIndex++] = x;
        vertexArray[currentVertexArrayIndex++] = y;
    }

    public void pushVec2(Vector2f vec) {
        vertexArray[currentVertexArrayIndex++] = vec.x;
        vertexArray[currentVertexArrayIndex++] = vec.y;
    }

    public void pushVec3(float x, float y, float z) {
        vertexArray[currentVertexArrayIndex++] = x;
        vertexArray[currentVertexArrayIndex++] = y;
        vertexArray[currentVertexArrayIndex++] = z;
    }

    public void pushVec3(Vector3f vec) {
        vertexArray[currentVertexArrayIndex++] = vec.x;
        vertexArray[currentVertexArrayIndex++] = vec.y;
        vertexArray[currentVertexArrayIndex++] = vec.z;
    }

    public void pushVec4(float x, float y, float z, float w) {
        vertexArray[currentVertexArrayIndex++] = x;
        vertexArray[currentVertexArrayIndex++] = y;
        vertexArray[currentVertexArrayIndex++] = z;
        vertexArray[currentVertexArrayIndex++] = w;
    }

    public void pushVec4(Vector4f vec) {
        vertexArray[currentVertexArrayIndex++] = vec.x;
        vertexArray[currentVertexArrayIndex++] = vec.y;
        vertexArray[currentVertexArrayIndex++] = vec.z;
        vertexArray[currentVertexArrayIndex++] = vec.w;
    }

    public void pushColor(Color color) {
        vertexArray[currentVertexArrayIndex++] = color.getR();
        vertexArray[currentVertexArrayIndex++] = color.getG();
        vertexArray[currentVertexArrayIndex++] = color.getB();
        vertexArray[currentVertexArrayIndex++] = color.getA();
    }
}
