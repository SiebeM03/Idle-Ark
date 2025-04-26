package woareXengine.util;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class Color {

    public static final Color WHITE = new Color(1.0f, 1.0f, 1.0f);
    public static final Color BLACK = new Color(0.0f, 0.0f, 0.0f);
    public static final Color RED = new Color(1.0f, 0.0f, 0.0f);
    public static final Color GREEN = new Color(0.0f, 1.0f, 0.0f);
    public static final Color BLUE = new Color(0.0f, 0.0f, 1.0f);
    public static final Color YELLOW = new Color(1.0f, 1.0f, 0.0f);
    public static final Color CYAN = new Color(0.0f, 1.0f, 1.0f);
    public static final Color MAGENTA = new Color(1.0f, 0.0f, 1.0f);

    private Vector3f col = new Vector3f();
    private float a = 1;

    public Color() {}

    public Color(Color color) {
        this.col.set(color.col);
        this.a = color.a;
    }

    /** Create a color from rgb values ranging from 0-1 */
    public Color(float r, float g, float b) {
        col.set(r, g, b);
    }

    public Color(Vector3f colour) {
        col.set(colour);
    }

    /** Create a color from rgba values ranging from 0-1 */
    public Color(Vector3f colour, float alpha) {
        col.set(colour);
        this.a = alpha;
    }

    /** Create a color from rgba values ranging from 0-1 */
    public Color(float r, float g, float b, float a) {
        col.set(r, g, b);
        this.a = a;
    }

    public Color(String hex) {
        this.col = new Vector3f(
                Integer.valueOf(hex.substring(1, 3), 16) / 255f,
                Integer.valueOf(hex.substring(3, 5), 16) / 255f,
                Integer.valueOf(hex.substring(5, 7), 16) / 255f
        );
    }

    public Color duplicate() {
        return new Color(this);
    }

    public void set(float r, float g, float b, float a) {
        col.set(r, g, b);
        this.a = a;
    }

    public void set(Vector4f color) {
        col.set(color.x, color.y, color.z);
        this.a = color.w;
    }

    public void setR(float r) {
        col.x = r;
    }

    public void setG(float g) {
        col.y = g;
    }

    public void setB(float b) {
        col.z = b;
    }

    public void setAlpha(float a) {
        this.a = a;
    }

    public Vector4f toVec4() {
        return new Vector4f(getR(), getG(), getB(), getA());
    }

    public Vector3f toVec3() {
        return new Vector3f(getR(), getG(), getB());
    }


    public float getR() {
        return col.x;
    }

    public float getG() {
        return col.y;
    }

    public float getB() {
        return col.z;
    }

    public float getA() {
        return a;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Color) {
            Color c = (Color) obj;
            return (col.x == c.col.x && col.y == c.col.y && col.z == c.col.z && a == c.a);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Color(" + col.x + ", " + col.y + ", " + col.z + ", " + a + ")";
    }
}
