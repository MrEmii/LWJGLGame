package dev.moon.engine.io;

import dev.moon.engine.math.Vector2f;
import dev.moon.engine.render.GLStateManager;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Window {

    private int width, height;
    private String title;


    public Window(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public static int getWidth() {
        return Display.getDisplayMode().getWidth();
    }

    public static int getHeight() {
        return Display.getDisplayMode().getHeight();
    }

    public static Vector2f getCenter() {

        return new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2);
    }

    public void init() {
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.setTitle(this.title);
            Display.create();
            Keyboard.create();
            Mouse.create();
            System.out.println(GLStateManager.getOpenGLVersion());
        } catch (LWJGLException e) {
            throw new IllegalStateException("GLFW wasn't initializing");
        }
    }

    public void update() {
        Display.update();
        Input.update();
    }

    public void swapBuffers() {
        try {
            Display.swapBuffers();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        Display.destroy();
        Keyboard.destroy();
        Mouse.destroy();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        Display.setTitle(title);
    }
}
