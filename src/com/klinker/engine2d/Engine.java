package com.klinker.engine2d;


import com.klinker.engine2d.draw.Scene;
import com.klinker.engine2d.inputs.InputManager;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.opengl.Texture;
import com.klinker.engine2d.utils.Log;
import com.klinker.engine2d.utils.PerformanceAnalyzer;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.system.MemoryUtil.NULL;


/**
 * The class that handles the game loop.
 */
public abstract class Engine {


    /**
     * The initial dimensions of the game window.
     */
    protected static Size<Integer> windowSize = new Size<>(1280, 720);

    /**
     * The parallel thread to run this game in.
     */
    private Thread thread;

    /**
     * The input manager for handling input.
     */
    private static InputManager inputManager = null;

    /**
     * An object for keeping track of the FPS.
     */
    private PerformanceAnalyzer analyzer;

    /**
     * The frame rate in fps.
     */
    protected static int frameRate;

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
     * Style of the engine {@link Engine.Style}
     */
    private Style style;

    /**
     * - {@link Engine.Style#RETRO}. with no anti-aliasing (nearest neighbor scaling)
     * - {@link Engine.Style#SMOOTH} with anti-aliasing.
     */
    public enum Style {
        RETRO, SMOOTH
    }


    /**
     * Starts the game thread.
     */
    protected void start() {
        //this.thread = new Thread(this, "game");
        //thread.start();
        this.analyzer = new PerformanceAnalyzer();
        run();
    }

    /**
     * This is where the game loop takes place. It will stop when the user presses the close
     * button.
     */
    public void run() {
        running = true;
        init();
        analyzer.start(window);
        while (running) {
            sync(frameRate);
            analyzer.addFrame();
            update();
            render();
            if (glfwWindowShouldClose(window)) running = false;
        }
        analyzer.stop();
        analyzer.logFrames();
        finish();
    }

    /**
     * Initializes OpenGL and the window.
     */
    private void init() {
        if (!glfwInit()) {
            System.err.println("GLFW did not initialize.");
            return;
        }

        // initializing the window, set OpenGL context to 3.2 and make it forward compatible.
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        //glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

        window = glfwCreateWindow(windowSize.width, windowSize.height, getWindowTitle(), NULL, NULL);
        if (window == NULL) {
            // TODO: 2/26/2017 error createing window
            Log.e("Error creating window");
            return;
        }
        glfwSetKeyCallback(window, inputManager.getKeyboard());    // set callbacks for keyboard input
        if (inputManager.getController() == null) inputManager.getKeyboard().setup();

        // set window position to centered
        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());    // getting the primary monitors properties
        glfwSetWindowPos(window, (vidMode.width() - windowSize.width) / 2, (vidMode.height() - windowSize.height) / 2);
        glfwMakeContextCurrent(window);     // set OS focus to this window
        GL.createCapabilities();
        glfwShowWindow(window);
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
        if (inputManager != null) inputManager.updateInputs(); //glfwPollEvents();       // update for input events
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

    /**
     * Closes the window and terminates OpenGL. When overriding, remember to call the super method
     */
    private void finish() {
        onFinish();
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    /**
     * Sets the current scene of the game.
     * // TODO: 3/15/2017 Add an animation somehow
     *
     * @param scene The scene to change to.
     */
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * Sets the style of the textures to be either {@link Style#RETRO} for pixel opengl,
     * or {@link Style#SMOOTH} for anti-aliasing.
     *
     * @param style The style of the game, either {@link Style#RETRO} or {@link Style#SMOOTH}
     */
    protected void setStyle(Style style) {
        this.style = style;
        Texture.RENDER_STYLE = style == Style.RETRO ? GL_NEAREST : GL_LINEAR;
    }

    /**
     * Times used to hold a constant framerate.
     *
     * @author kappa (On the LWJGL Forums)
     */
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


    /**
     * Gets the title for the window.
     *
     * @return The title for the window.
     */
    public abstract String getWindowTitle();

    /**
     * A function that is called so subclasses can be notified when you close.
     * It is called before OpenGL and the window exit.
     */
    protected abstract void onFinish();

    public void setInputManager(InputManager manager) {
        inputManager = manager;
    }

    public static InputManager getInputManager() {
        return inputManager;
    }

}