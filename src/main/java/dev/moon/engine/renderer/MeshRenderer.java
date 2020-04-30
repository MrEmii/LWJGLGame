package dev.moon.engine.renderer;

import dev.moon.engine.render.objects.Mesh;
import dev.moon.engine.graphics.GameComponent;
import dev.moon.engine.render.materials.Material;
import dev.moon.engine.render.shaders.Shader;

public class MeshRenderer extends GameComponent {

    private Mesh mesh;
    private Material material;

    public MeshRenderer(Mesh mesh, Material material) {
        this.mesh = mesh;
        this.material = material;
    }

    public void render(double delta, Shader shader, RenderManager manager) {
        shader.bind();
        shader.updateUniforms(getTransform(), material, manager);
        mesh.render();
    }

    @Override
    public void add(RenderManager renderManager) {

    }
}
