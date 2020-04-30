package dev.moon.engine.render.shaders;

import dev.moon.engine.math.Matrix4f;
import dev.moon.engine.math.Transform;
import dev.moon.engine.render.materials.Material;
import dev.moon.engine.renderer.RenderManager;

public class ForwardAmbient extends Shader {

    private static ForwardAmbient instance = new ForwardAmbient();

    public ForwardAmbient() {

        super();

        addVertexShaderFromFile("forward/ambient/ambientVertex.glsl");
        addFragmentShaderFromFile("forward/ambient/ambientFragment.glsl");
        compileShader();

        setAttributeLocation("position", 0);
        setAttributeLocation("texCoord", 1);
        addUniform("MVP");
        addUniform("ambientIntensity");
    }

    public static ForwardAmbient getInstance() {
        return instance;
    }

    @Override
    public void updateUniforms(Transform transform, Material material, RenderManager renderManager) {
        super.updateUniforms(transform, material, renderManager);
        Matrix4f worldMatrix = transform.getTransformation();
        Matrix4f projectedMatrix = renderManager.getMainCamera().getViewProjection().mul(worldMatrix);

        setUniform("MVP", projectedMatrix);
        setUniform("ambientIntensity", renderManager.getAmbientLight());
    }

}
