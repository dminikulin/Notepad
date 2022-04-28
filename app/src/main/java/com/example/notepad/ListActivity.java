package com.example.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        DBManager dbManager = new DBManager(this);
        List<Note> notes = Arrays.asList(
                new Note(0, "A", LocalDateTime.now(), "AAAAAAAAAAAA"),
                new Note(0, "B", LocalDateTime.now(), "BBBBBBBBBBBB"),
                new Note(0, "C", LocalDateTime.now(), "CCCCCCCCCCCC")
        );
        for(Note note : notes){
            dbManager.save(note);
        }
        notes= dbManager.findAllToList();
        notes.forEach(note -> Log.e("FF", note.toString() + "\n"));

        ListView listView = findViewById(R.id.listView);

        /*ArrayAdapter<Note> arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, notes
        );
        listView.setAdapter(arrayAdapter);*/

        /*Cursor cursor = dbManager.findAllToCursor();
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(
                this, android.R.layout.two_line_list_item, cursor,
                new String[]{DBManager.HEADER, DBManager.TIME},
                new int[]{android.R.id.text1, android.R.id.text2}, 0
        );
        listView.setAdapter(cursorAdapter);*/

        Cursor cursor = dbManager.findAllToCursor();
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(
                this, R.layout.list_item, cursor,
                new String[]{DBManager.ID, DBManager.HEADER, DBManager.TIME},
                new int[]{R.id.idItemList, R.id.headerItemList, R.id.timeItemList}, 0
        );
        listView.setAdapter(cursorAdapter);
    }
}