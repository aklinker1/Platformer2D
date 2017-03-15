package com.klinker.engine2d.utils;


import org.lwjgl.glfw.GLFW;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;



/**
 * This class is for logging the frames.
 */
public class PerformanceAnalyzer implements Runnable {



    /**
     * The interval to check the fps on.
     */
    private static final long INTERVAL = 1000;



    /**
     * The thread this will run on.
     */
    private Thread thread = null;

    /**
     * The window to update the title
     */
    private long window;

    /**
     * Whether or not the game is finished.
     */
    private boolean stop = false;

    /**
     * The list of FPS's, an item per interval.
     */
    private LinkedList<Double> fpsHistory;

    /**
     * A counter for calculating the FPS.
     */
    private int frames = 0;



    /**
     * Initializes and starts the thread.
     */
    public void start(long window) {
        this.window = window;
        fpsHistory = new LinkedList<>();
        thread = new Thread(this, "performance");
        thread.start();
    }

    /**
     * Stops the analyzer thread.
     */
    public void stop() {
        this.stop = true;
    }

    /**
     * The loop that waits then checks the FPS.
     */
    @Override
    public void run() {
        while (!stop) {
            try {
                Thread.sleep(INTERVAL);
            } catch (Exception e) {}
            fpsHistory.addLast(getFrames() / (INTERVAL / 1000.0));
            GLFW.glfwSetWindowTitle(window, new StringBuilder("Platformer2D ").append(fpsHistory.getLast()).append("/60"));
            clearFrames();
        }
    }



    /**
     * Clears the frame counter in sync with the game thread.
     */
    private synchronized void clearFrames() {
        frames = 0;
    }

    /**
     * Adds a frame to the counter in sync with the game thread.
     */
    public synchronized void addFrame() {
        frames++;
    }

    /**
     * Gets the frame counter in sync with the game thread.
     */
    private synchronized int getFrames() {
        return frames;
    }



    /**
     * Outputs the list of FPS's to "log/frames.log"
     */
    public void logFrames() {
        StringBuilder builder = new StringBuilder("Second\tFPS\n");
        int i = 1;
        double sum = 0;
        for (double d : fpsHistory) {
            builder.append(i * INTERVAL / 1000.0);
            builder.append('\t');
            builder.append(d);
            builder.append('\n');
            i++;
            sum += d;
        }

        try (FileWriter fWriter = new FileWriter("log/frames.log");
             BufferedWriter writer = new BufferedWriter(fWriter)) {
            writer.write(builder.toString());
            writer.write("\nAverage\t" + (sum / (i - 1)));
        } catch (IOException e) {
            System.err.println("Could not log fps");
            e.printStackTrace();
        }
    }

}
