package dev.moon.engine.render.shaders;

import dev.moon.engine.math.Matrix4f;
import dev.moon.engine.math.Transform;
import dev.moon.engine.render.materials.Material;
import dev.moon.engine.render.shaders.components.BaseLight;
import dev.moon.engine.render.shaders.components.PointLight;
import dev.moon.engine.render.shaders.components.SpotLight;
import dev.moon.engine.renderer.RenderManager;

public class ForwardSpotLight extends Shader {

    private static ForwardSpotLight instance = new ForwardSpotLight();

    public ForwardSpotLight() {

        super();

        addVertexShaderFromFile("forward/spot/fv-spotlight.glsl");
        addFragmentShaderFromFile("forward/spot/ff-spotlight.glsl");


        setAttributeLocation("position", 0);
        setAttributeLocation("texCoord", 1);
        setAttributeLocation("normal", 2);

        compileShader();


        addUniform("model");
        addUniform("MVP");

        addUniform("specularIntensity");
        addUniform("specularPower");
        addUniform("eyePos");

        addUniform("spotLight.pointLight.base.color");
        addUniform("spotLight.pointLight.base.intensity");
        addUniform("spotLight.pointLight.attenuation.constant");
        addUniform("spotLight.pointLight.attenuation.linear");
        addUniform("spotLight.pointLight.attenuation.exponent");
        addUniform("spotLight.pointLight.position");
        addUniform("spotLight.pointLight.range");
        addUniform("spotLight.direction");
        addUniform("spotLight.cutoff");

    }

    public static ForwardSpotLight getInstance() {
        return instance;
    }

    @Override
    public void updateUniforms(Transform transform, Material material, RenderManager manager) {
        super.updateUniforms(transform, material, manager);
        Matrix4f worldMatrix = transform.getTransformation();
        Matrix4f projectedMatrix = manager.getMainCamera().getViewProjection().mul(worldMatrix);

        setUniform("model", worldMatrix);
        setUniform("MVP", projectedMatrix);

        setUniform("spotLight", (SpotLight) manager.getCurrentLight());

        setUniformf("specularIntensity", material.getFloat("specularIntensity"));
        setUniformf("specularPower", material.getFloat("specularPower"));

        setUniform("eyePos", manager.getMainCamera().getTransform().getTransformedPosition());
    }

    private void setUniform(String s, SpotLight spotLight) {
        setUniform(s + ".pointLight", (PointLight) spotLight);
        setUniform(s + ".direction", spotLight.getDirection());
        setUniformf(s + ".cutoff", spotLight.getCutoff());
    }

    private void setUniform(String s, PointLight pointLight) {
        setUniform(s + ".base", (BaseLight) pointLight);
        setUniformf(s + ".attenuation.constant", pointLight.getConstant());
        setUniformf(s + ".attenuation.linear", pointLight.getLinear());
        setUniformf(s + ".attenuation.exponent", pointLight.getExponent());
        setUniform(s + ".position", pointLight.getTransform().getPosition());
        setUniformf(s + ".range", pointLight.getRange());
    }

    private void setUniform(String uniformname, BaseLight base) {
        setUniform(uniformname + ".color", base.getColor());
        setUniformf(uniformname + ".intensity", base.getIntensity());
    }
}