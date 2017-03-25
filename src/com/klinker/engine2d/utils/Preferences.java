package com.klinker.engine2d.utils;

import java.io.*;
import java.util.HashMap;

public class Preferences {

    private HashMap<String, Object> data;


    public Preferences() {
        data = new HashMap<>();
    }

    public Preferences(String path) {
        data = readFromFile(path);
    }


    public boolean hasData() {
        Log.d("hasData? " + data);
        return data != null;
    }


    public void put(String key, Object data) {
        this.data.put(key, data);
    }

    public int getInt(String key) {
        if (data == null) return -1;
        else return (int) data.get(key);
    }

    public float getFloat(String key) {
        if (data == null) return -1f;
        else return (float) data.get(key);
    }

    public String getString(String key) {
        if (data == null) return null;
        else return (String) data.get(key);
    }

    public Object getObject(String key) {
        if (data == null) return null;
        else return data.get(key);
    }


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
