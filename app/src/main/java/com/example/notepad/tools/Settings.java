package com.example.notepad.tools;

import android.content.Context;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Settings {
    private static final String FILE_NAME = "settings";
    private static Settings settings;

    public static Settings getSettings() {
        if (settings == null) settings = new Settings();
        return settings;
    }

    public static void save(Context context) {
        Gson gson = new Gson();
        try {
            FileOutputStream stream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            String json = gson.toJson(settings);
            stream.write(json.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void load(Context context) {
        Gson gson = new Gson();
        try {
            FileInputStream stream = context.openFileInput(FILE_NAME);
            StringBuilder builder = new StringBuilder();
            int s;
            while ((s = stream.read()) > -1) builder.append((char) s);
            settings = gson.fromJson(builder.toString(), Settings.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int fontSize;
}
