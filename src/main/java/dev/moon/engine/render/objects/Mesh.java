package dev.moon.engine.render.objects;

import dev.moon.engine.math.Vertex;
import dev.moon.engine.render.mesh.IndexedModel;
import dev.moon.engine.render.mesh.Model;
import dev.moon.utils.Utils;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Mesh {
    private int vbo, ibo;
    private int size;

    public Mesh(Vertex[] vertices, int[] indices) {
        this(vertices, indices, false);
    }

    public Mesh(Vertex[] vertices, int[] indices, boolean calcNormals) {
        initMeshData();
        addVertices(vertices, indices, calcNormals);
    }

    public Mesh(String filename) {
        initMeshData();
        loadMesh(filename);
    }

    private void initMeshData() {
        vbo = glGenBuffers();
        ibo = glGenBuffers();
        size = 0;
    }

    public void render() {

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12);
        glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * 4, 20);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glDrawElements(GL_TRIANGLES, size, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
    }

    private void addVertices(Vertex[] vertices, int[] indices, boolean calcNormals) {

        size = indices.length;

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, Utils.createFlippedBuffer(vertices), GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, Utils.createFlippedBuffer(indices), GL_STATIC_DRAW);
    }


    private Mesh loadMesh(String fileName) {
        String[] splitArray = fileName.split("\\.");
        String ext = splitArray[splitArray.length - 1];

        if (!ext.equals("obj")) {
            System.err.println("Error: File format not supported for mesh data: " + ext);
            new Exception().printStackTrace();
            System.exit(1);
        }

        Model model = new Model(fileName);
        IndexedModel indexedModel = model.toIndexedModel();
        indexedModel.calcNormals();

        ArrayList<Vertex> vertices = new ArrayList<>();

        for (int i = 0; i < indexedModel.getPositions().size(); i++) {

            vertices.add(new Vertex(
                    indexedModel.getPositions().get(i),
                    indexedModel.getTexCoords().get(i),
                    indexedModel.getNormals().get(i)));
        }

        Vertex[] vertexData = new Vertex[vertices.size()];
        vertices.toArray(vertexData);

        Integer[] indexData = new Integer[indexedModel.getIndices().size()];
        indexedModel.getIndices().toArray(indexData);

        addVertices(vertexData, Utils.toIntArray(indexData), false);

        return null;
    }
}
