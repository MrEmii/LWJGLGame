package dev.moon.engine.render.shaders.components;

import dev.moon.engine.math.Vector3f;
import dev.moon.engine.render.shaders.ForwardPoint;
import dev.moon.engine.renderer.RenderManager;

public class PointLight extends BaseLight {

    private static final int COLOR_DEPTH = 256;

    private Vector3f attenuation;
    private float range;

    public PointLight(Vector3f color, float intensity, Vector3f attenuation) {
        super(color, intensity);
        this.attenuation = attenuation;

        float a = attenuation.getZ();
        float b = attenuation.getY();
        float c = attenuation.getX() - COLOR_DEPTH * getIntensity() * getColor().max();

        this.range = (float) ((-b + Math.sqrt(b * b - 4 * a * c)) / (2 * a));

        setShader(new ForwardPoint());
    }

    public float getConstant() {
        return attenuation.getX();
    }

    public float getLinear() {
        return attenuation.getY();
    }

    public float getExponent() {
        return attenuation.getZ();
    }

    public void setAttenuation(Vector3f attenuation) {
        this.attenuation = attenuation;
    }

    public void setConstant(float var) {
        attenuation.setX(var);
    }

    public void setLinear(float var) {
        attenuation.setY(var);
    }

    public void setExponent(float var) {
        attenuation.setZ(var);
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }


    public void add(RenderManager renderManager) {
        renderManager.add(this);
    }
}
