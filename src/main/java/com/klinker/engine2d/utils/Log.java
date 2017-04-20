package com.klinker.engine2d.utils;

public class Log {

    public static boolean enabled = true;

    public static void d(boolean newLine, String message) {
        if (enabled)
            if (newLine) System.out.println(message);
            else System.out.print(message);
    }

    public static void d(String message) {
        d(true, message);
    }

    public static void d(String format, Object ... args) {
        d(true, String.format(format, args));
    }

    public static void d(boolean newLine, String format, Object ... args) {
        d(newLine, String.format(format, args));
    }

    public static void e(String message) {
        if (enabled) System.err.println(message);
    }

    public static void e(String message, Exception e) {
        if (enabled) {
            System.err.println(message);
            e.printStackTrace(System.err);
        }
    }
}
