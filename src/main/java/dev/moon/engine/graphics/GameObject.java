package dev.moon.engine.graphics;

import dev.moon.engine.math.Transform;
import dev.moon.engine.render.shaders.Shader;
import dev.moon.engine.renderer.RenderManager;

import java.util.ArrayList;

public class GameObject {

    private ArrayList<GameObject> children;
    private ArrayList<GameComponent> components;

    private Transform transform;

    public GameObject() {
        this.children = new ArrayList<GameObject>();
        this.components = new ArrayList<GameComponent>();
        transform = new Transform();
    }

    public void addChild(GameObject object) {
        children.add(object);
        object.getTransform().setParent(getTransform());
    }

    public void addChild(GameObject... object) {
        for (GameObject gameObject : object) {
            children.add(gameObject);
            gameObject.getTransform().setParent(transform);
        }
    }

    public void addComponent(GameComponent... object) {
        for (GameComponent gameComponent : object) {
            components.add(gameComponent);
            gameComponent.setParent(this);
        }
    }

    public GameObject addComponent(GameComponent object) {
        components.add(object);
        object.setParent(this);
        return this;
    }


    public void input(double delta) {
        for (GameComponent object : components) {
            object.input(delta);
        }

        for (GameObject object : children) {
            object.input(delta);
        }
    }

    public void update(double delta) {
        transform.update();
        for (GameComponent object : components) {
            object.input(delta);
        }

        for (GameObject object : children) {
            object.update(delta);
        }
    }

    public void render(double delta, Shader shader, RenderManager manager) {
        for (GameComponent object : components) {
            object.render(delta, shader, manager);
        }

        for (GameObject object : children) {
            object.render(delta, shader, manager);
        }
    }

    public void add(RenderManager renderManager) {
        for (GameComponent object : components) {
            object.add(renderManager);
        }

        for (GameObject object : children) {
            object.add(renderManager);
        }
    }

    public Transform getTransform() {
        return transform;
    }
}

