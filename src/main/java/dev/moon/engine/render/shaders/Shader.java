package dev.moon.engine.render.shaders;

import dev.moon.Moon;
import dev.moon.engine.math.Matrix4f;
import dev.moon.engine.math.Transform;
import dev.moon.engine.math.Vector3f;
import dev.moon.engine.render.materials.Material;
import dev.moon.engine.renderer.RenderManager;
import dev.moon.utils.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

public class Shader {

    private RenderManager renderManager;

    private int program;
    private HashMap<String, Integer> uniforms;

    public Shader() {
        program = glCreateProgram();

        uniforms = new HashMap<String, Integer>();

        if (program == 0) {
            System.err.println("Shader creation failed: Could not find valid memory location in constructor");
            System.exit(1);
        }
    }

    private static String loadShader(String filename) {

        StringBuilder shaderSource = new StringBuilder();

        BufferedReader shaderReader = null;

        try {
            shaderReader = new BufferedReader(new FileReader(Moon.class.getClassLoader().getResource("assets/shaders/" + filename).getFile()));
            String line;
            while ((line = shaderReader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return shaderSource.toString();
    }

    public void bind() {
        glUseProgram(program);
    }

    public void addVertexShaderFromFile(String text) {
        addProgram(loadShader(text), GL_VERTEX_SHADER);
    }

    public void addGeometryShaderFromFile(String text) {
        addProgram(loadShader(text), GL_GEOMETRY_SHADER);
    }

    public void addFragmentShaderFromFile(String text) {
        addProgram(loadShader(text), GL_FRAGMENT_SHADER);
    }

    public void compileShader() {
        glLinkProgram(program);

        if (glGetProgram(program, GL_LINK_STATUS) == 0) {
            System.err.println("Shader creation failed: Could not find valid memory when linking shader");
            System.exit(1);
        }

        glValidateProgram(program);
        if (glGetProgram(program, GL_VALIDATE_STATUS) == 0) {
            System.err.println("Shader creation failed: Could not find valid memory when validate shader");
            System.exit(1);
        }

    }

    public void addUniform(String uniform) {
        int uniformLocation = glGetUniformLocation(program, uniform);

        if (uniformLocation == 0xFFFFFFFF) {
            System.err.println("Error: Could not find uniform: " + uniformLocation + uniform);
            new Exception().printStackTrace();
            System.exit(1);
        }

        uniforms.put(uniform, uniformLocation);

    }

    public void setUniformi(String uniformname, int value) {
        glUniform1i(uniforms.get(uniformname), value);
    }

    public void setUniformf(String uniformname, float value) {
        glUniform1f(uniforms.get(uniformname), value);
    }

    public void setUniform(String uniformname, Vector3f value) {
        glUniform3f(uniforms.get(uniformname), value.getX(), value.getY(), value.getZ());
    }

    public void setUniform(String uniformname, Matrix4f value) {
        glUniformMatrix4(uniforms.get(uniformname), true, Utils.createFilppedBuffer(value));
    }

    public void updateUniforms(Transform transform, Material material, RenderManager renderManager) {
        material.getTexture("diffuse").bind();
    }

    public void setAttributeLocation(String attrib, int location) {
        glBindAttribLocation(program, location, attrib);
    }

    private void addProgram(String text, int type) {
        int shader = glCreateShader(type);

        if (shader == 0) {
            System.err.println("Shader creation failed: Could not find valid memory when adding shader");
            System.exit(1);
        }

        glShaderSource(shader, text);
        glCompileShader(shader);

        if (glGetShader(shader, GL_COMPILE_STATUS) == 0) {
            System.err.println(glGetShaderInfoLog(shader, 1024));
            System.exit(1);
        }

        glAttachShader(program, shader);
    }
}
