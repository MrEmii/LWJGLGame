package dev.moon;

import dev.moon.game.Game;
import dev.moon.engine.io.Time;
import dev.moon.engine.io.Window;
import dev.moon.engine.render.GLStateManager;
import dev.moon.engine.renderer.RenderManager;
import org.lwjgl.opengl.Display;

public class Moon implements Runnable {

    public Window windows;
    public Thread game;
    public Game theGame;
    public RenderManager renderManager;
    private double frameTime;

    public static void main(String[] args) {
        new Moon().start();
    }

    public void start() {
        game = new Thread(this, "Game");
        game.start();
    }

    public void init() {
        System.out.println("Initializing Game");
        windows = new Window("Moon", 1280, 720);
        windows.init();
        this.frameTime = 1.0 / 120;
        this.theGame = new Game();
        this.renderManager = new RenderManager();
    }


    public void render(double delta) {
        GLStateManager.clear();
        theGame.render(renderManager, delta);
    }

    public void update(Double delta) {
        theGame.update(delta);
        renderManager.update(delta);
        windows.update();
    }

    public void destroy() {
        windows.destroy();
    }

    @Override
    public void run() {
        this.init();
        int frames = 0;
        double frameCounter = 0;

        double lastTime = Time.getTime();
        double unprocessedTime = 0;
        while (!Display.isCloseRequested()) {
            boolean render = false;
            double startTime = Time.getTime();
            double passedTime = startTime - lastTime;
            lastTime = startTime;

            unprocessedTime += passedTime;
            frameCounter += passedTime;
            while (unprocessedTime > frameTime) {
                render = true;

                unprocessedTime -= frameTime;

                if (Display.isCloseRequested())
                    destroy();

                update(frameTime);

                if (frameCounter >= 1.0) {
                    System.out.println(frames);
                    frames = 0;
                    frameCounter = 0;
                }
            }
            if (render) {
                render(frameTime);
                frames++;
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        destroy();
    }
}
