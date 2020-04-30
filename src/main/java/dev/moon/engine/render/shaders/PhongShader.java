package dev.moon.engine.render.shaders;

import dev.moon.engine.math.Matrix4f;
import dev.moon.engine.math.Transform;
import dev.moon.engine.math.Vector3f;
import dev.moon.engine.render.materials.Material;
import dev.moon.engine.render.shaders.components.BaseLight;
import dev.moon.engine.render.shaders.components.DirectionalLight;
import dev.moon.engine.render.shaders.components.PointLight;
import dev.moon.engine.render.shaders.components.SpotLight;
import dev.moon.engine.renderer.RenderManager;

public class PhongShader extends Shader {

    private static final int MAX_POINT_LIGHTS = 4;
    private static final int MAX_SPOT_LIGHTS = 4;


    private static final PhongShader instance = new PhongShader();
    private static Vector3f ambientLight = new Vector3f(1, 1, 1);
    private static DirectionalLight directionalLight = new DirectionalLight(new Vector3f(0, 0, 0), 0);
    private static PointLight[] pointLights = new PointLight[MAX_POINT_LIGHTS];
    private static SpotLight[] spotLights = new SpotLight[MAX_SPOT_LIGHTS];

    public PhongShader() {
        super();

        addVertexShaderFromFile("phong/phongVertex.glsl");
        addFragmentShaderFromFile("phong/phongFragment.glsl");
        compileShader();

        addUniform("transform");
        addUniform("baseColor");
        addUniform("transformProjected");
        addUniform("ambientLight");

        addUniform("specularIntensity");
        addUniform("specularPower");
        addUniform("eyePos");


        addUniform("directionalLight.base.color");
        addUniform("directionalLight.base.intensity");
        addUniform("directionalLight.direction");

        for (int i = 0; i < MAX_POINT_LIGHTS; i++) {
            addUniform("pointLights[" + i + "].base.color");
            addUniform("pointLights[" + i + "].base.intensity");
            addUniform("pointLights[" + i + "].attenuation.constant");
            addUniform("pointLights[" + i + "].attenuation.linear");
            addUniform("pointLights[" + i + "].attenuation.exponent");
            addUniform("pointLights[" + i + "].position");
            addUniform("pointLights[" + i + "].range");
        }

        for (int i = 0; i < MAX_SPOT_LIGHTS; i++) {
            addUniform("spotLights[" + i + "].pointLight.base.color");
            addUniform("spotLights[" + i + "].pointLight.base.intensity");
            addUniform("spotLights[" + i + "].pointLight.attenuation.constant");
            addUniform("spotLights[" + i + "].pointLight.attenuation.linear");
            addUniform("spotLights[" + i + "].pointLight.attenuation.exponent");
            addUniform("spotLights[" + i + "].pointLight.position");
            addUniform("spotLights[" + i + "].pointLight.range");
            addUniform("spotLights[" + i + "].direction");
            addUniform("spotLights[" + i + "].cutoff");
        }
    }

    public static PhongShader getInstance() {
        return instance;
    }

    public static Vector3f getAmbientLight() {
        return ambientLight;
    }

    public static void setAmbientLight(Vector3f ambientLight) {
        PhongShader.ambientLight = ambientLight;
    }

    public static DirectionalLight getDirectionalLight() {
        return directionalLight;
    }

    public static void setDirectionalLight(DirectionalLight directionalLight) {
        PhongShader.directionalLight = directionalLight;
    }

    public static PointLight[] getPointLights() {
        return pointLights;
    }

    public static void setPointLights(PointLight[] pointLights) {
        if (pointLights.length > MAX_POINT_LIGHTS) {
            System.err.println("Error: You passed in too many point lights. MAx allowed is " + MAX_POINT_LIGHTS);
            new Exception().printStackTrace();
            System.exit(1);
        }
        PhongShader.pointLights = pointLights;
    }

    public static void setSpotLights(SpotLight[] spotLights) {
        if (spotLights.length > MAX_SPOT_LIGHTS) {
            System.err.println("Error: You passed in too many spots lights. MAx allowed is " + MAX_SPOT_LIGHTS);
            new Exception().printStackTrace();
            System.exit(1);
        }
        PhongShader.spotLights = spotLights;
    }

    @Override
    public void updateUniforms(Transform transform, Material material, RenderManager manager) {
        super.updateUniforms(transform, material, manager);

        Matrix4f worldMatrix = transform.getTransformation();
        Matrix4f projectedMatrix = manager.getMainCamera().getViewProjection().mul(worldMatrix);

        setUniform("transformProjected", projectedMatrix);
        setUniform("transform", worldMatrix);
        setUniform("baseColor", material.getVector("color"));

        setUniform("ambientLight", ambientLight);
        setUniform("directionalLight", directionalLight);

        for (int i = 0; i < pointLights.length; i++) {
            if (pointLights[i] != null)
                setUniform("pointLights[" + i + "]", pointLights[i]);
        }
        for (int i = 0; i < spotLights.length; i++) {

            setUniform("spotLights[" + i + "]", spotLights[i]);
        }

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

    public void setUniform(String uniformname, DirectionalLight value) {
        setUniform(uniformname + ".base", value);
        setUniform(uniformname + ".direction", value.getDirection());
    }
}
