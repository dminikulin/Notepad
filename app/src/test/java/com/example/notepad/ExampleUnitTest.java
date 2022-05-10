package com.example.notepad;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.notepad.data.Note;
import com.google.gson.Gson;

import java.time.LocalDateTime;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void JsonTime(){
        Gson gson = new Gson();
        Note note = new Note(1, "AAAAAA", LocalDateTime.now(), "huigreajmklfgdmkol.fmklagnjk,dfsngjkfsokdl");
        String json = gson.toJson(note);
        System.out.println(json);
        note = gson.fromJson(json, Note.class);
        System.out.println(note);
    }
}