package dev.moon.engine.render.materials;

import dev.moon.Moon;
import org.newdawn.slick.opengl.TextureLoader;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

public class Texture {

    private int id;

    public Texture(String fileName) {
        this(loadTexture(fileName));
    }

    public Texture(int id) {
        this.id = id;
    }

    public static int loadTexture(String filename) {

        String[] splitArray = filename.split("\\.");
        String ext = splitArray[splitArray.length - 1];

        try {
            return TextureLoader.getTexture(ext.toUpperCase(), Moon.class.getClassLoader().getResource("assets/textures/" + filename).openStream()).getTextureID();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return -1;
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);

    }

    public int getId() {
        return id;
    }
}
