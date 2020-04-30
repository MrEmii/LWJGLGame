package dev.moon.engine.render.shaders;

import dev.moon.engine.math.Matrix4f;
import dev.moon.engine.math.Transform;
import dev.moon.engine.render.materials.Material;
import dev.moon.engine.render.shaders.components.BaseLight;
import dev.moon.engine.render.shaders.components.DirectionalLight;
import dev.moon.engine.renderer.RenderManager;

public class ForwardDirectional extends Shader {

    private static ForwardDirectional instance = new ForwardDirectional();

    public ForwardDirectional() {

        super();

        addVertexShaderFromFile("forward/directional/forwardVertex-directional.glsl");
        addFragmentShaderFromFile("forward/directional/forwardFragment-directional.glsl");


        setAttributeLocation("position", 0);
        setAttributeLocation("texCoord", 1);
        setAttributeLocation("normal", 2);

        compileShader();


        addUniform("model");
        addUniform("MVP");

        addUniform("specularIntensity");
        addUniform("specularPower");
        addUniform("eyePos");

        addUniform("directionalLight.base.color");
        addUniform("directionalLight.base.intensity");
        addUniform("directionalLight.direction");

    }

    public static ForwardDirectional getInstance() {
        return instance;
    }

    @Override
    public void updateUniforms(Transform transform, Material material, RenderManager manager) {
        super.updateUniforms(transform, material, manager);
        Matrix4f worldMatrix = transform.getTransformation();
        Matrix4f projectedMatrix = manager.getMainCamera().getViewProjection().mul(worldMatrix);

        setUniform("model", worldMatrix);
        setUniform("MVP", projectedMatrix);


        setUniform("directionalLight", (DirectionalLight) manager.getCurrentLight());

        setUniformf("specularIntensity", material.getFloat("specularIntensity"));
        setUniformf("specularPower", material.getFloat("specularPower"));

        setUniform("eyePos", manager.getMainCamera().getTransform().getTransformedPosition());


    }

    public void setUniform(String uniformname, DirectionalLight value) {
        setUniform(uniformname + ".base", (BaseLight) value);
        setUniform(uniformname + ".direction", value.getDirection());
    }

    private void setUniform(String uniformname, BaseLight base) {
        setUniform(uniformname + ".color", base.getColor());
        setUniformf(uniformname + ".intensity", base.getIntensity());
    }
}