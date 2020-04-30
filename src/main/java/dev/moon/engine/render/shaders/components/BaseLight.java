package dev.moon.engine.render.shaders.components;

import dev.moon.engine.graphics.GameComponent;
import dev.moon.engine.math.Transform;
import dev.moon.engine.math.Vector3f;
import dev.moon.engine.render.shaders.Shader;
import dev.moon.engine.renderer.RenderManager;

public class BaseLight extends GameComponent {

    private Vector3f color;
    private float intensity;
    private Shader shader;

    public BaseLight(Vector3f color, float intensity) {
        this.color = color;
        this.intensity = intensity;
    }

    public Shader getShader() {
        return shader;
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

}
