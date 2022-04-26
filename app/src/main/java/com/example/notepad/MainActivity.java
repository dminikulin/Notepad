package com.example.notepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.notepad.databinding.ActivityMainBinding;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //
        binding.helloButton.setOnClickListener(view -> {
            binding.textNote.setText("Hello world");
        });
        binding.clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.textNote.setText("");
            }
        });
        Log.e("FF", "create");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
//            return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        return super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.settingsMenu:
                Toast.makeText(MainActivity.this, "Settings Menu Item",
                        Toast.LENGTH_LONG).show();
                FileManager.write(MainActivity.this, "");
                break;
            case R.id.saveMenu:
                Toast.makeText(MainActivity.this,
                        "The text has been saved successfully",
                        Toast.LENGTH_LONG).show();

                String textToSave = binding.textNote.getText().toString();
                FileManager.write(MainActivity.this, textToSave);
                break;
            case R.id.loadMenu:
                Toast.makeText(
                        MainActivity.this,
                        "File is open successfully",
                        Toast.LENGTH_LONG).show();
                String textFromFile = FileManager.read(MainActivity.this);
                binding.textNote.setText(textFromFile);
                break;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("FF", "start");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("FF", "pause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("FF", "resume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("FF", "restart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("FF", "stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("FF", "destroy");
    }
}