package woareXengine.mainEngine.gameObjects;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import woareXengine.io.userInputs.Input;
import woareXengine.mainEngine.Engine;
import woareXengine.util.Transform;

public abstract class Camera {
    private float nearPlane;
    private float farPlane;

    private boolean dirtyView = true;
    private boolean dirtyProjection = true;

    private Vector2f position;

    private Vector2f projectionSize = new Vector2f(Engine.window().getScreenCoordWidth(), Engine.window().getScreenCoordHeight());

    private Matrix4f projectionMatrix = new Matrix4f();
    private Matrix4f viewMatrix = new Matrix4f();
    public Matrix4f projectionViewMatrix = new Matrix4f();

    public Camera(float nearPlane, float farPlane) {
        this.position = new Vector2f();
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
        Engine.window().addSizeChangeListener((width, height) -> {
            projectionSize.set(1280, 720);
            dirtyProjection = true;
        });
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
        this.dirtyView = true;
    }

    public void setPosition(Vector2f position) {
        this.position.set(position);
        this.dirtyView = true;
    }

    public Vector2f getPosition() {
        return position;
    }


    public Matrix4f getProjectionMatrix() {
        if (dirtyProjection) {
            updateProjectionMatrix();
        }
        return projectionMatrix;
    }

    private void updateProjectionMatrix() {
        projectionMatrix.identity();
        projectionMatrix.ortho(0.0f, projectionSize.x, 0.0f, projectionSize.y, nearPlane, farPlane);
        this.dirtyProjection = false;
    }

    public Matrix4f getViewMatrix() {
        if (dirtyView) {
            updateViewMatrix();
        }
        return viewMatrix;
    }

    private void updateViewMatrix() {
        Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
        Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
        viewMatrix.identity();
        viewMatrix.lookAt(
                new Vector3f(position.x, position.y, 20.0f),
                cameraFront.add(position.x, position.y, 0.0f),
                cameraUp
        );
        this.dirtyView = false;
    }

    public float getMouseWorldX() {
        float currentX = Input.mouse().getX() * 2.0f - 1.0f;
        return transformScreenToWorld(currentX, 0).x;
    }

    public float getMouseWorldY() {
        float currentY = Input.mouse().getY() * 2.0f - 1.0f;
        return transformScreenToWorld(0, currentY).y;
    }

    protected Vector4f transformScreenToWorld(float x, float y) {
        Vector4f tmp = new Vector4f(x, y, 0, 1);

        Matrix4f viewProjection = new Matrix4f();
        Matrix4f inverseView = new Matrix4f();
        Matrix4f inverseProjection = new Matrix4f();

        getViewMatrix().invert(inverseView);
        getProjectionMatrix().invert(inverseProjection);
        inverseView.mul(inverseProjection, viewProjection);

        tmp.mul(viewProjection);

        return tmp;
    }


    public boolean isOnScreen(Transform transform) {
        return transform.getX() + transform.getWidth() > getMinX() - 100 &&
                       transform.getX() < getMaxX() + 100 &&
                       transform.getY() + transform.getHeight() > getMinY() - 100 &&
                       transform.getY() < getMaxY() + 100;
    }

    public float getMinX() {
        return transformScreenToWorld(-1, 0).x;
    }

    public float getMaxX() {
        return transformScreenToWorld(1, 0).x;
    }

    public float getMinY() {
        return transformScreenToWorld(0, -1).y;
    }

    public float getMaxY() {
        return transformScreenToWorld(0, 1).y;
    }
}
