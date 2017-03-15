package com.klinker.engine2d;


import com.klinker.engine2d.graphics.Scene;
import com.klinker.engine2d.graphics.Shader;
import com.klinker.engine2d.graphics.Texture;
import com.klinker.engine2d.inputs.Input;
import com.klinker.engine2d.maths.Size;
import com.klinker.engine2d.utils.PerformanceAnalyzer;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL12;

import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.glfw.GLFW.*;


/**
 * The class that handles the game loop.
 */
public abstract class Engine implements Runnable {


    /**
     * The initial dimensions of the game window.
     */
    public static Size<Integer> SIZE = new Size<>(1280, 720);

    /**
     * The parallel thread to run this game in.
     */
    private Thread thread;

    /**
     * An object for keeping track of the FPS.
     */
    private PerformanceAnalyzer analyzer;

    /**
     * Whether or not the parallel thread is running.
     */
    private boolean running = false;

    /**
     * The OpenGL reference to the window that is opened.
     */
    private long window;

    /**
     * The current scene that is being ran.
     */
    private Scene scene;

    /**
     * Style of the engine, {@link Engine.Style#RETRO} with no anti-aliasing (nearest neighbor scaling),
     * or {@link Engine.Style#SMOOTH} with anti-aliasing.
     */
    public static Style STYLE;

    public enum Style {
        RETRO, SMOOTH
    }


    /**
     * Starts the game thread.
     */
    public void start() {
        this.thread = new Thread(this, "game");
        thread.start();
        this.analyzer = new PerformanceAnalyzer();
    }

    /**
     * This is where the game loop takes place. It will stop when the user presses the close
     * button.
     */
    @Override
    public void run() {
        running = true;
        init();
        analyzer.start(window);
        while (running) {
            sync(60);
            analyzer.addFrame();
            update();
            render();
            if (glfwWindowShouldClose(window)) running = false;
        }
        analyzer.stop();
        analyzer.logFrames();
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    /**
     * Initializes OpenGL and the window.
     */
    private void init() {
        if (!glfwInit()) {
            System.err.println("GLFW did not initialize.");
            return;
        }

        // initializing the window
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
        window = glfwCreateWindow(SIZE.width, SIZE.height, getWindowTitle(), NULL, NULL);
        if (window == NULL) {
            // TODO: 2/26/2017 error createing window
            return;
        }
        glfwSetKeyCallback(window, new Input());    // set callbacks for keyboard input

        // set window position to centered
        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());    // getting the primary monitors properties
        glfwSetWindowPos(window, (vidMode.width() - SIZE.width) / 2, (vidMode.height() - SIZE.height) / 2);
        glfwMakeContextCurrent(window);     // set OS focus to this window
        glfwShowWindow(window);
        GL.createCapabilities();
        //glfwSetWindowIcon(window, );      TODO: 2/26/2017 Add Icon

        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glActiveTexture(GL_TEXTURE1);
        glEnable(GL_DEPTH_TEST);

        System.out.println("OpenGL: " + glGetString(GL_VERSION));

        // sets up the orthographic projection for the scene
        if (scene != null) {
            scene.init();
        } else {
            System.err.println("Scene is null, so shader's proj_matrix and tex cannot be set.");
        }
    }

    /**
     * Updates the game by checking for inputs.
     */
    private void update() {
        scene.update();
        glfwPollEvents();       // poll for input events
    }

    /**
     * Render the current scene. Clears the x&y and z buffers to prevent frame buildup.
     */
    private void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        scene.render();
        int error = glGetError();
        if (error != GL_NO_ERROR) {
            System.err.println("OpenGL error: " + error);
        }
        glfwSwapBuffers(window);
    }


    public abstract String getWindowTitle();


    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setStyle(Style style) {
        Engine.STYLE = style;
        Texture.RENDER_STYLE = style == Style.RETRO ? GL_NEAREST : GL_LINEAR;
    }

    public void setSize(Size<Integer> newSize) {
        this.SIZE = newSize;
    }

    private long variableYieldTime, lastTime;

    /**
     * An accurate sync method that adapts automatically
     * to the system it runs on to provide reliable results.
     *
     * @param fps The desired frame rate, in frames per second
     * @author kappa (On the LWJGL Forums)
     */
    private void sync(int fps) {
        if (fps <= 0) return;

        long sleepTime = 1000000000 / fps; // nanoseconds to sleep this frame
        // yieldTime + remainder micro & nano seconds if smaller than sleepTime
        long yieldTime = Math.min(sleepTime, variableYieldTime + sleepTime % (1000 * 1000));
        long overSleep = 0; // time the sync goes over by

        try {
            while (true) {
                long t = System.nanoTime() - lastTime;

                if (t < sleepTime - yieldTime) {
                    Thread.sleep(1);
                } else if (t < sleepTime) {
                    // burn the last few CPU cycles to ensure accuracy
                    Thread.yield();
                } else {
                    overSleep = t - sleepTime;
                    break; // exit while loop
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lastTime = System.nanoTime() - Math.min(overSleep, sleepTime);

            // auto tune the time sync should yield
            if (overSleep > variableYieldTime) {
                // increase by 200 microseconds (1/5 a ms)
                variableYieldTime = Math.min(variableYieldTime + 200 * 1000, sleepTime);
            } else if (overSleep < variableYieldTime - 200 * 1000) {
                // decrease by 2 microseconds
                variableYieldTime = Math.max(variableYieldTime - 2 * 1000, 0);
            }
        }
    }

}