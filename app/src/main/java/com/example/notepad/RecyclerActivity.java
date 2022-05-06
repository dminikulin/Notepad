package com.example.notepad;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.ContentResolver;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.notepad.adapters.NotesAdapter;
import com.example.notepad.data.DBManager;
import com.example.notepad.data.Note;
import com.example.notepad.databinding.ActivityRecyclerBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity {
    private ActivityRecyclerBinding binding;
    private ActivityResultLauncher<String[]> saveLauncher;
    private ActivityResultLauncher<String[]> loadLauncher;
    private ActivityResultLauncher<String> createDocLauncher;
    private ActivityResultLauncher<String[]> readDocLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecyclerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        List<Note> notes = Arrays.asList(
                new Note(0, "A", LocalDateTime.now(), "AAAAAAAAAAAA"),
                new Note(0, "B", LocalDateTime.now(), "BBBBBBBBBBBB"),
                new Note(0, "C", LocalDateTime.now(), "CCCCCCCCCCCC")
        );

        DBManager manager = new DBManager(this);
        notes.forEach(manager::save);
        notes = manager.findAllToList();

        binding.recyclerView.setAdapter(new NotesAdapter(notes, this));
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(layoutManager);
        /*saveLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                savePermission -> {
                    if(savePermission){
                        Log.e("FF", "savePermission");
                    }
                }
        );*/
        createDocLauncher = registerForActivityResult(
                new ActivityResultContracts.CreateDocument(),
                (Uri uri) -> {
                    try {
                        Log.e("FF", uri.toString());
                        ContentResolver resolver = getContentResolver();
                        OutputStream outputStream = resolver.openOutputStream(uri);
                        outputStream.write("Test".getBytes(StandardCharsets.UTF_8));
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

        );

        saveLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                savePermission -> {
                    if (savePermission.get(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Log.e("FF", "savePermission");
                        createDocLauncher.launch(null);
                    }
                }
        );

        readDocLauncher = registerForActivityResult(
                new ActivityResultContracts.OpenDocument(),
                (Uri uri) -> {
                    try {
                        Log.e("FF", uri.toString());
                        ContentResolver resolver = getContentResolver();
                        InputStream inputStream = resolver.openInputStream(uri);
                        StringBuilder sb = new StringBuilder();
                        int s;
                        while ((s = inputStream.read()) > -1) sb.append((char) s);
                        Log.e("READ DOC", sb.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );

        loadLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                loadPermissions -> {
                    if (loadPermissions.get(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        readDocLauncher.launch(null);
                    } else {
                        Toast.makeText(RecyclerActivity.this, "Permission needed to load file",
                                Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveMenu:
                saveLauncher.launch(new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                });
                break;
            case R.id.loadMenu:
//                loadLauncher.launch(new String[]{
//                        Manifest.permission.READ_EXTERNAL_STORAGE
//                });
                Snackbar snackbar = Snackbar.make(binding.recyclerView, "SNACK",
                        BaseTransientBottomBar.LENGTH_INDEFINITE)
                        .setBackgroundTint(Color.rgb(0, 100, 0));
                snackbar.setAction("CLOSE", view -> {
                    snackbar.dismiss();
                });
                snackbar.show();
                break;
        }
        return true;
    }
}