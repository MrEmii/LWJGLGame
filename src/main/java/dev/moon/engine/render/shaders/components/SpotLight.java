package dev.moon.engine.render.shaders.components;

import dev.moon.engine.math.Vector3f;
import dev.moon.engine.render.shaders.ForwardSpotLight;

public class SpotLight extends PointLight {


    private float cutoff;

    public SpotLight(Vector3f color, float intensity, Vector3f attenuation, float cutoff) {
        super(color, intensity, attenuation);
        this.cutoff = cutoff;
        setShader(ForwardSpotLight.getInstance());
    }

    public void setPosition(Vector3f pos) {
        getTransform().getPosition().set(pos);
    }

    public void setPosition(float x, float y, float z) {
        getTransform().getPosition().set(x, y, z);
    }


    public Vector3f getDirection() {
        return getTransform().getRot().getForward();
    }


    public float getCutoff() {
        return cutoff;
    }

    public void setCutoff(float cutoff) {
        this.cutoff = cutoff;
    }


}
