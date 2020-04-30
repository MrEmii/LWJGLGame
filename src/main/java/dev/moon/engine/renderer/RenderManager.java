package dev.moon.engine.renderer;

import dev.moon.engine.graphics.GameObject;
import dev.moon.engine.io.Window;
import dev.moon.engine.math.Vector3f;
import dev.moon.engine.render.GLStateManager;
import dev.moon.engine.render.objects.Camera;
import dev.moon.engine.render.shaders.ForwardAmbient;
import dev.moon.engine.render.shaders.Shader;
import dev.moon.engine.render.shaders.components.BaseLight;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class RenderManager {

    private Camera mainCamera;
    private Vector3f ambientLight;

    private ArrayList<BaseLight> lights;
    private BaseLight currentLight;

    public RenderManager() {

        GLStateManager.initGraphics();
        lights = new ArrayList<>();
       // mainCamera = new Camera((float) Math.toRadians(70.0f), (float) Window.getWidth() / (float) Window.getHeight(), 0.01f, 1000.0f);
        ambientLight = new Vector3f(1f, 1f, 1f);

    }

    public void render(GameObject object, double delta) {

        lights.clear();
        object.add(this);

        Shader forwardAmbient = ForwardAmbient.getInstance();

        object.render(delta, forwardAmbient, this);

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE);
        glDepthMask(false);
        glDepthFunc(GL_EQUAL);

        lights.forEach(baseLight -> {
            currentLight = baseLight;
            object.render(delta, baseLight.getShader(), this);
        });

        glDepthFunc(GL_LESS);
        glDepthMask(true);
        glDisable(GL_BLEND);
    }


    public Vector3f getAmbientLight() {
        return ambientLight;
    }

    public void setAmbientLight(Vector3f ambientLight) {
        this.ambientLight = ambientLight;
    }

    public void update(double delta) {

    }

    public Camera getMainCamera() {
        return mainCamera;
    }

    public void setMainCamera(Camera mainCamera) {
        this.mainCamera = mainCamera;
    }

    public BaseLight getCurrentLight() {
        return currentLight;
    }

    public void add(BaseLight light) {
        lights.add(light);
    }

    public void add(Camera camera) {
        mainCamera = camera;
    }


}
