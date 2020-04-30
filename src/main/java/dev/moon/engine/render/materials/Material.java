package dev.moon.engine.render.materials;

import dev.moon.engine.math.Vector3f;

import java.util.HashMap;

public class Material {

    private static final Texture TEXTURE_NULL = new Texture(Texture.loadTexture("test.png"));

    private HashMap<String, Texture> textureHashMap;
    private HashMap<String, Vector3f> vectorHashMap;
    private HashMap<String, Float> floatHashMap;

    public Material() {
        textureHashMap = new HashMap<>();
        vectorHashMap = new HashMap<>();
        floatHashMap = new HashMap<>();
    }

    public void addTexture(String name, Texture texture) {
        textureHashMap.put(name, texture);
    }

    public void addVector(String name, Vector3f vector3f) {
        vectorHashMap.put(name, vector3f);
    }

    public void addFloat(String name, float value) {
        floatHashMap.put(name, value);
    }

    public Texture getTexture(String name) {
        return textureHashMap.getOrDefault(name, TEXTURE_NULL);
    }

    public Vector3f getVector(String name) {
        return vectorHashMap.getOrDefault(name, new Vector3f(0, 0, 0));

    }

    public float getFloat(String name) {
      return floatHashMap.getOrDefault(name, -1f);

    }

}
