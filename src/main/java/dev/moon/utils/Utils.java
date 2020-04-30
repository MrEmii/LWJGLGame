package dev.moon.utils;

import dev.moon.engine.math.Matrix4f;
import dev.moon.engine.math.Vertex;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

public class Utils {

    public static FloatBuffer createFloatBuffer(int size) {
        return org.lwjgl.BufferUtils.createFloatBuffer(size);
    }

    public static IntBuffer createIntBuffer(int size) {
        return org.lwjgl.BufferUtils.createIntBuffer(size);
    }

    public static IntBuffer createFlippedBuffer(int... indices) {
        IntBuffer buffer = createIntBuffer(indices.length);
        buffer.put(indices);
        buffer.flip();
        return buffer;
    }

    public static FloatBuffer createFlippedBuffer(Vertex[] vertices) {
        FloatBuffer buffer = createFloatBuffer(vertices.length * Vertex.SIZE);
        for (int i = 0; i < vertices.length; i++) {
            buffer.put(vertices[i].getPos().getX());
            buffer.put(vertices[i].getPos().getY());
            buffer.put(vertices[i].getPos().getZ());

            buffer.put(vertices[i].getTexCoord().getX());
            buffer.put(vertices[i].getTexCoord().getY());

            buffer.put(vertices[i].getNormal().getX());
            buffer.put(vertices[i].getNormal().getY());
            buffer.put(vertices[i].getNormal().getZ());

        }

        buffer.flip();

        return buffer;
    }

    public static FloatBuffer createFilppedBuffer(Matrix4f value) {
        FloatBuffer buffer = createFloatBuffer(4 * 4);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buffer.put(value.get(i, j));
            }
        }

        buffer.flip();
        return buffer;
    }

    public static String[] removeEmptyString(String[] data) {
        ArrayList<String> result = new ArrayList<>();

        for (int i = 0; i < data.length; i++) {

            if (!data[i].equalsIgnoreCase(""))
                result.add(data[i]);

        }

        String[] res = new String[result.size()];
        result.toArray(res);
        return res;
    }

    public static int[] toIntArray(Integer[] indexData) {

        int[] res = new int[indexData.length];

        for (int i = 0; i < indexData.length; i++) {
            res[i] = indexData[i];
        }

        return res;


    }
}
