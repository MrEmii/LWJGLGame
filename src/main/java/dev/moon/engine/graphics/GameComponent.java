package dev.moon.engine.graphics;

import dev.moon.engine.math.Transform;
import dev.moon.engine.render.shaders.Shader;
import dev.moon.engine.renderer.RenderManager;

public abstract class GameComponent {

    private GameObject parent;

    public void input(double delta){}

    public void update(double delta){}

    public void render(double delta, Shader shader, RenderManager manager){}

    public void add(RenderManager renderManager){}

    public void setParent(GameObject parent) {
        this.parent = parent;
    }

    public Transform getTransform() {
        return parent.getTransform();
    }
}
