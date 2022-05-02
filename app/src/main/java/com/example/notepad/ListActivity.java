package com.example.notepad;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.notepad.data.DBManager;
import com.example.notepad.data.Note;
import com.example.notepad.tools.Keys;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> resultLauncher;
    private List<Note> notes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        DBManager dbManager = new DBManager(this);
        notes = Arrays.asList(
                new Note(0, "A", LocalDateTime.now(), "AAAAAAAAAAAA"),
                new Note(0, "B", LocalDateTime.now(), "BBBBBBBBBBBB"),
                new Note(0, "C", LocalDateTime.now(), "CCCCCCCCCCCC")
        );
        for(Note note : notes){
//            dbManager.save(note);
        }
        notes= dbManager.findAllToList();
        notes.forEach(note -> Log.e("FF", note.toString() + "\n"));

        ListView listView = findViewById(R.id.listView);

        ArrayAdapter<Note> arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, notes
        );
        listView.setAdapter(arrayAdapter);

        /*Cursor cursor = dbManager.findAllToCursor();
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(
                this, android.R.layout.two_line_list_item, cursor,
                new String[]{DBManager.HEADER, DBManager.TIME},
                new int[]{android.R.id.text1, android.R.id.text2}, 0
        );
        listView.setAdapter(cursorAdapter);*/

        /*Cursor cursor = dbManager.findAllToCursor();
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(
                this, R.layout.list_item, cursor,
                new String[]{DBManager.ID, DBManager.HEADER, DBManager.TIME},
                new int[]{R.id.idItemList, R.id.headerItemList, R.id.timeItemList}, 0
        );
        listView.setAdapter(cursorAdapter);*/

        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Log.e("FF", " " + result);
                    if(result.getResultCode() == RESULT_OK){
                        Intent data = result.getData();
                        Note note = (Note) data.getSerializableExtra(Keys.NOTE_KEY.name());
                        int index = data.getIntExtra("index", -1);
                        if(note != null && index > -1){
                            dbManager.update(note);
                            notes.set(index, note);
                        }
                    }
                    else if(result.getResultCode() == RESULT_CANCELED){

                    }
                }
        );

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(ListActivity.this, NoteActivity.class);

//            intent.putExtra("note_ID", (int)l);
            Note note = dbManager.findByID((int)l);
            intent.putExtra(Keys.NOTE_KEY.name(), note);
            intent.putExtra("index", i);

//            startActivity(intent);
//            finish();
            resultLauncher.launch(intent);
        });
    }
}