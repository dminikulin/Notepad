package com.example.notepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.notepad.databinding.ActivityNoteBinding;

import java.time.LocalDateTime;

public class NoteActivity extends AppCompatActivity {
    private ActivityNoteBinding binding;
    private Note note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        Intent data = new Intent();
//        data.putExtra("val", 123);
//        setResult(1, data);
        /*DBManager manager = new DBManager(this);
        int id = getIntent().getIntExtra("note_ID", 0);
        Note note = manager.findByID(id);*/

        note = (Note) getIntent().getSerializableExtra("note");

        Log.e("FF", String.valueOf(note));
        binding.noteId.setText(String.valueOf(note.getId()));
        binding.noteHeader.setText(note.getHeader());
        binding.noteTime.setText(note.getTimeInString());
        binding.noteText.setText(note.getText());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.saveNoteMenu){
            if(note == null) note = new Note();
            note.setHeader(binding.noteHeader.getText().toString());
            note.setTime(LocalDateTime.now());
            note.setText(binding.noteTime.getText().toString());

            Intent intent = new Intent();
            intent.putExtra("note", note);
            intent.putExtra("index", getIntent().getIntExtra("index", -1));
            setResult(RESULT_OK, intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }
}