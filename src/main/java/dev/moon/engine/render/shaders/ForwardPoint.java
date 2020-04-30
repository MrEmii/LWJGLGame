package dev.moon.engine.render.shaders;

import dev.moon.engine.math.Matrix4f;
import dev.moon.engine.math.Transform;
import dev.moon.engine.render.materials.Material;
import dev.moon.engine.render.shaders.components.BaseLight;
import dev.moon.engine.render.shaders.components.PointLight;
import dev.moon.engine.renderer.RenderManager;

public class ForwardPoint extends Shader {

    private static ForwardPoint instance = new ForwardPoint();

    public ForwardPoint() {

        super();

        addVertexShaderFromFile("forward/point/fv-point.glsl");
        addFragmentShaderFromFile("forward/point/ff-point.glsl");


        setAttributeLocation("position", 0);
        setAttributeLocation("texCoord", 1);
        setAttributeLocation("normal", 2);

        compileShader();


        addUniform("model");
        addUniform("MVP");

        addUniform("specularIntensity");
        addUniform("specularPower");
        addUniform("eyePos");

        addUniform("pointLight.base.color");
        addUniform("pointLight.base.intensity");
        addUniform("pointLight.attenuation.constant");
        addUniform("pointLight.attenuation.linear");
        addUniform("pointLight.attenuation.exponent");
        addUniform("pointLight.position");
        addUniform("pointLight.range");

    }

    public static ForwardPoint getInstance() {
        return instance;
    }

    @Override
    public void updateUniforms(Transform transform, Material material, RenderManager manager) {
        super.updateUniforms(transform, material, manager);
        Matrix4f worldMatrix = transform.getTransformation();
        Matrix4f projectedMatrix = manager.getMainCamera().getViewProjection().mul(worldMatrix);

        setUniform("model", worldMatrix);
        setUniform("MVP", projectedMatrix);

        setUniform("pointLight", (PointLight) manager.getCurrentLight());

        setUniformf("specularIntensity", material.getFloat("specularIntensity"));
        setUniformf("specularPower", material.getFloat("specularPower"));
        setUniform("eyePos", manager.getMainCamera().getTransform().getTransformedPosition());

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