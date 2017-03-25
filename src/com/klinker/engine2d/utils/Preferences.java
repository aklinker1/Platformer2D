package com.klinker.engine2d.utils;

import java.io.*;
import java.util.HashMap;

public class Preferences {

    private HashMap<String, Object> data;


    /**
     * Creates a mapping of the settings options.
     */
    public Preferences() {
        data = new HashMap<>();
    }

    /**
     * Creates a preference object from a file. That file was created through a {@link ObjectOutputStream}.
     *
     * @param path The path to the file containing the preference data.
     */
    public Preferences(String path) {
        data = readFromFile(path);
    }


    /**
     * @return Whether or not the preference has been initialized.
     */
    public boolean hasData() {
        return data != null;
    }


    /**
     * @see HashMap#put(Object, Object)
     */
    public void put(String key, Object data) {
        this.data.put(key, data);
    }

    /**
     * Returns an integer located in a map with a given key.
     *
     * @param key The map's key.
     * @return The integer at {@param key}
     */
    public int getInt(String key) {
        if (data == null) return -1;
        else return (int) data.get(key);
    }

    /**
     * Returns a float located in a map with a given key.
     *
     * @param key The map's key.
     * @return The float at {@param key}
     */
    public float getFloat(String key) {
        if (data == null) return -1f;
        else return (float) data.get(key);
    }

    /**
     * Returns a string located in a map with a given key.
     *
     * @param key The map's key.
     * @return The string at {@param key}
     */
    public String getString(String key) {
        if (data == null) return null;
        else return (String) data.get(key);
    }


    /**
     * Returns an object located in a map with a given key.
     *
     * @param key The map's key.
     * @return The object at {@param key}
     */
    public Object getObject(String key) {
        if (data == null) return null;
        else return data.get(key);
    }


    /**
     * Outouts the map to a file.
     * @param filePath The path to the file.
     * @return Whether or not it successfully wrote to the file.
     */
    public boolean outputToFile(String filePath) {
        try (FileOutputStream stream = new FileOutputStream(filePath);
                ObjectOutputStream outStream = new ObjectOutputStream(stream)) {
            outStream.writeObject(data);
            stream.close();
            outStream.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Creates a mapping from a file.
     * @param path The path to the file to create the map from.
     * @return The map from the given file.
     */
    private HashMap<String, Object> readFromFile(String path) {
        File inputFile = new File(path);
        if (inputFile.exists()) try (FileInputStream fIn = new FileInputStream(inputFile);
                ObjectInputStream inputStream = new ObjectInputStream(fIn)) {
            @SuppressWarnings("unchecked")
            HashMap<String, Object> data = (HashMap<String, Object>) inputStream.readObject();
            return data;
        } catch (Exception e) {
            return null;
        } else {
            return null;
        }
    }


    /**.
     * @return The map's data in JSON format.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Prefs {\n");
        if (data != null) for (String key : data.keySet()) {
            builder.append("    \"").append(key).append("\":").append(data.get(key)).append('\n');
        }
        builder.append("}");
        return builder.toString();
    }

}
