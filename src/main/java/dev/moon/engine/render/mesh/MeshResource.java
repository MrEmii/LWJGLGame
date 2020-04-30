package dev.moon.engine.render.mesh;

import static org.lwjgl.opengl.GL15.*;

public class MeshResource {

    private int vbo, ibo, size;

    public MeshResource() {

        vbo = glGenBuffers();
        ibo = glGenBuffers();
        size = 0;
    }

    @Override
    protected void finalize() throws Throwable {

        glDeleteBuffers(vbo);
        glDeleteBuffers(ibo);

    }


    public int getVbo() {
        return vbo;
    }

    public int getIbo() {
        return ibo;
    }
}
