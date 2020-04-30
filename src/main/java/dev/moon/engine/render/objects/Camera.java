package dev.moon.engine.render.objects;

import dev.moon.engine.graphics.GameComponent;
import dev.moon.engine.io.Input;
import dev.moon.engine.io.Window;
import dev.moon.engine.math.Matrix4f;
import dev.moon.engine.math.Quaternion;
import dev.moon.engine.math.Vector2f;
import dev.moon.engine.math.Vector3f;
import dev.moon.engine.renderer.RenderManager;
import org.lwjgl.opengl.Display;

public class Camera extends GameComponent {

    private static final Vector3f yAxis = new Vector3f(0, 1, 0);
    boolean mouseLocked = false;
    private Matrix4f projection;

    public Camera(float fov, float aspect, float zNear, float zFar) {
        this.projection = new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
    }

    public Vector3f getLeft() {
        return getTransform().getRot().getLeft();
    }

    public Vector3f getRight() {

        return getTransform().getRot().getRight();
    }

    @Override
    public void add(RenderManager renderManager) {
        renderManager.add(this);
    }

    @Override
    public void input(double delta) {
        float movAmt = (float) (10 * delta);
        float sensitivity = 0.5f;
        if (Input.getKey(Input.KEY_ESCAPE)) {
            Input.setCursor(true);
            mouseLocked = false;
        }
        if (Input.getMouseDown(0)) {
            Input.setMousePosition(Window.getCenter());
            Input.setCursor(false);
            mouseLocked = true;
        }

        if (Input.getKey(Input.KEY_W)) {
            move(getForward(), movAmt);
        }

        if (Input.getKey(Input.KEY_S)) {
            move(getForward(), -movAmt);
        }

        if (Input.getKey(Input.KEY_A)) {
            move(getLeft(), movAmt);
        }

        if (Input.getKey(Input.KEY_D)) {

            move(getRight(), movAmt);
        }

        if (mouseLocked) {
            Vector2f deltaPos = Input.getMousePosition().sub(Window.getCenter());

            boolean rotY = deltaPos.getX() != 0;
            boolean rotX = deltaPos.getY() != 0;

            if (rotY)
                getTransform().rotate(yAxis, (float) Math.toRadians(deltaPos.getX() * sensitivity));
            if (rotX)
                getTransform().rotate(getTransform().getRot().getRight(), (float) Math.toRadians(-deltaPos.getY() * sensitivity));
            if (rotY || rotX)
                Input.setMousePosition(new Vector2f(Display.getDisplayMode().getWidth() / 2, Display.getDisplayMode().getHeight() / 2));
        }
    }

    public void move(Vector3f dir, float amt) {

        getTransform().setPos(getPos().add(dir.mul(amt)));

    }

    public Vector3f getPos() {
        return getTransform().getPosition();
    }

    public Vector3f getForward() {
        return getTransform().getRot().getForward();
    }

    public Vector3f getUp() {
        return getTransform().getRot().getUp();
    }

    public Matrix4f getViewProjection() {

        Matrix4f cameraRotation = getTransform().getTransformedRot().conjugate().toRotationMatrix();
        Vector3f cameraPos = getTransform().getTransformedPosition().mul(-1);

        Matrix4f cameraTranslation = new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());


        return projection.mul(cameraRotation.mul(cameraTranslation));
    }

    public Matrix4f getProjection() {
        return projection;
    }
}
