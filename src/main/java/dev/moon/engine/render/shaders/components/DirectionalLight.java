package dev.moon.engine.render.shaders.components;

import dev.moon.engine.math.Vector3f;
import dev.moon.engine.render.shaders.ForwardDirectional;
import dev.moon.engine.renderer.RenderManager;

public class DirectionalLight extends BaseLight {

    public DirectionalLight(Vector3f color, float intensity) {
        super(color, intensity);
        setShader(ForwardDirectional.getInstance());
    }

    public Vector3f getDirection() {
        return getTransform().getTransformedRot().getForward();
    }

    public void add(RenderManager renderManager) {
        renderManager.add(this);
    }
}
