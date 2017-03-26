package com.klinker.engine2d.utils;



import java.io.*;
import java.util.LinkedList;


public class FileUtils {



    /**
     * Private constructor to prevent external instantiation.
     */
    private FileUtils() { }



    /**
     * Loads a file contents as a string.
     * @param file The file to read.
     * @return The contents of {@param file} as a string.
     */
    public static String loadAsString(String file) {
        StringBuilder result = new StringBuilder();
        try (FileReader fReader = new FileReader(file);
             BufferedReader reader = new BufferedReader(fReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line + '\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * Loads a file contents as an array of lines.
     * @param file The file to read.
     * @return The contents of {@param file} as a string[] split by lines.
     */
    public static String[] loadAsLineArray(String file) {
        LinkedList<String> strings = new LinkedList<>();
        try (FileReader fReader = new FileReader(file);
             BufferedReader reader = new BufferedReader(fReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                strings.addLast(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] result = new String[strings.size()];
        strings.toArray(result);
        return result;
    }



}