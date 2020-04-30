package dev.moon.game;

import dev.moon.engine.graphics.GameObject;
import dev.moon.engine.io.Window;
import dev.moon.engine.math.Quaternion;
import dev.moon.engine.math.Vector2f;
import dev.moon.engine.math.Vector3f;
import dev.moon.engine.math.Vertex;
import dev.moon.engine.render.materials.Material;
import dev.moon.engine.render.materials.Texture;
import dev.moon.engine.render.objects.Camera;
import dev.moon.engine.render.objects.Mesh;
import dev.moon.engine.render.shaders.components.DirectionalLight;
import dev.moon.engine.render.shaders.components.PointLight;
import dev.moon.engine.render.shaders.components.SpotLight;
import dev.moon.engine.renderer.MeshRenderer;
import dev.moon.engine.renderer.RenderManager;

public class Game {

    private GameObject root = new GameObject();

    public Game() {

        root = new GameObject();

        float fieldDepth = 10.0f;
        float fieldWidth = 10.0f;

        Vertex[] vertices = new Vertex[]{new Vertex(new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
                new Vertex(new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2f(0.0f, 1.0f)),
                new Vertex(new Vector3f(fieldWidth * 3, 0.0f, -fieldDepth), new Vector2f(1.0f, 0.0f)),
                new Vertex(new Vector3f(fieldWidth * 3, 0.0f, fieldDepth * 3), new Vector2f(1.0f, 1.0f))};

        int indices[] = {0, 1, 2,
                2, 1, 3};


        Vertex[] vertices2 = new Vertex[]{new Vertex(new Vector3f(-fieldWidth / 10, 0.0f, -fieldDepth / 10), new Vector2f(0.0f, 0.0f)),
                new Vertex(new Vector3f(-fieldWidth / 10, 0.0f, fieldDepth / 10 * 3), new Vector2f(0.0f, 1.0f)),
                new Vertex(new Vector3f(fieldWidth / 10 * 3, 0.0f, -fieldDepth / 10), new Vector2f(1.0f, 0.0f)),
                new Vertex(new Vector3f(fieldWidth / 10 * 3, 0.0f, fieldDepth / 10 * 3), new Vector2f(1.0f, 1.0f))};

        int indices2[] = {0, 1, 2,
                2, 1, 3};

        Mesh mesh2 = new Mesh(vertices2, indices2, true);

        Mesh mesh = new Mesh(vertices, indices, true);
        Material material = new Material();
        material.addTexture("diffuse", new Texture("textura.png"));
        material.addFloat("specularIntensity", 1);
        material.addFloat("specularPower", 8);
        material.addVector("", new Vector3f(0, 0, 0));

        Material material1 = new Material();
        material1.addTexture("diffuse", new Texture("afternoon-haze-32x.png"));
        material1.addFloat("specularIntensity", 1);
        material1.addFloat("specularPower", 8);
        material1.addVector("", new Vector3f(0, 0, 0));

        Mesh tempMesh = new Mesh("monigote.obj");
        Mesh tempMesh1 = new Mesh("mono.obj");
        MeshRenderer meshRenderer = new MeshRenderer(mesh, material);

        GameObject planeObject = new GameObject();

        planeObject.addComponent(meshRenderer);
        planeObject.getTransform().getPosition().set(0, -1, 5);

        GameObject directionalLightObject = new GameObject();
        DirectionalLight directionalLight = new DirectionalLight(new Vector3f(0, 0f, 1f), 0.4f);
        directionalLightObject.addComponent(directionalLight);

        GameObject pointLightObject = new GameObject();
        PointLight pointLight = new PointLight(new Vector3f(0, 1, 0), 0.4f, new Vector3f(0, 0, 1));
        pointLightObject.addComponent(pointLight);

        GameObject spotLightObject = new GameObject();
        SpotLight spotLight = new SpotLight(new Vector3f(1, 1, 1), 0.4f,
                new Vector3f(0, 0, .1f), 0.7f);
        spotLightObject.addComponent(spotLight);

        spotLight.setPosition(5, 0, 5);
        spotLight.getTransform().setRot(new Quaternion(new Vector3f(0, 1, 0), (float) Math.toRadians(90f)));
        addObject(planeObject);
        addObject(directionalLightObject);
        addObject(pointLightObject);
        addObject(spotLightObject);


        GameObject testMesh1 = new GameObject().addComponent(new MeshRenderer(mesh2, material));
        GameObject testMesh2 = new GameObject().addComponent(new MeshRenderer(mesh2, material));
        GameObject testMesh3 = new GameObject().addComponent(new MeshRenderer(tempMesh, material1));
        GameObject testMesh4 = new GameObject().addComponent(new MeshRenderer(tempMesh1, material1));
        testMesh3.getTransform().getPosition().set(0, 2, 0);
        testMesh3.getTransform().setRot(new Quaternion(new Vector3f(0, 1, 0), 0.4f));


        root.addChild(new GameObject().addComponent(new Camera((float) Math.toRadians(70.0f), (float) Window.getWidth() / (float) Window.getHeight(), 0.01f, 1000.0f)));

        addObject(testMesh3);
       // addObject(testMesh4);

        directionalLight.getTransform().setRot(new Quaternion(new Vector3f(1, 0, 0), (float) Math.toRadians(-45)));

    }

    public void update(double delta) {
        root.update(delta);
        root.input(delta);
    }

    public void render(RenderManager renderManager, double delta) {
        renderManager.render(root, delta);
    }

    public void addObject(GameObject object) {

        getRoot().addChild(object);

    }

    public GameObject getRoot() {
        if (root == null) {
            root = new GameObject();
        }
        return root;
    }
}
