package com.example.notepad;

import android.content.Context;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Settings {
    private static final String FILE_NAME = "settings";
    private static Settings settings;

    public static Settings getSettings() {
        if(settings==null) settings = new Settings();
        return settings;
    }

    public static void save(Context context){
        Gson gson = new Gson();
        try {
            FileOutputStream stream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
