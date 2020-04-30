package dev.moon.engine.gui;

import static org.lwjgl.opengl.GL11.*;

public class Gui {

    public static void drawRect(int x, int y, int width, int height, int color) {

        float f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        glColor4f(f, f1, f2, f3);
        glBegin(GL_QUADS);
        glVertex2f(x, y);
        glVertex2f(x + width, y);
        glVertex2f(x + width, y + height);
        glVertex2f(x, y + height);
        glEnd();
    }

    public static void drawImage(int x, int y, int width, int height) {
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(x, y);
        glTexCoord2f(1, 0);
        glVertex2f(x + width, y);
        glTexCoord2f(1, 1);
        glVertex2f(x + width, y + height);
        glTexCoord2f(0, 1);
        glVertex2f(x, y + height);
        glEnd();
    }

}
