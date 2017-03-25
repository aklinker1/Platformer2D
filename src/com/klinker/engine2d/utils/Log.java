package com.klinker.engine2d.utils;

public class Log {

    public static boolean enabled = true;

    public static void d(String message) {
        if (enabled) System.out.println(message);
    }

    public static void e(String message) {
        if (enabled) System.err.println(message);
    }

}