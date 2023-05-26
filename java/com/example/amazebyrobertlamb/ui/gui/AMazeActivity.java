package com.example.amazebyrobertlamb.ui.gui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.example.amazebyrobertlamb.R;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amazebyrobertlamb.databinding.ActivityMainBinding;

import java.util.Random;

public class AMazeActivity extends AppCompatActivity {

    private static int level;
    private static boolean isPerfect;
    private String builder;
    private static int seed;
    private Button exp;
    private Button rev;
    private ToggleButton perfectSwitch;
    private ActivityMainBinding binding;
    private Spinner build;
    private SeekBar levelval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = this.getApplicationContext();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Random ran = new Random();
        seed = ran.nextInt();
        exp = (Button)findViewById(R.id.button_Explore);
        rev = (Button)findViewById(R.id.revisitButton);
        perfectSwitch = (ToggleButton)findViewById(R.id.whetherPerfect);
        build = (Spinner)findViewById(R.id.spinner);
        levelval = (SeekBar)findViewById(R.id.seekBar);
        exp.setOnClickListener(view -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("maze_size", level);
            editor.putString("builder_algorithm", builder);
            editor.putBoolean("rooms_enabled", isPerfect);
            editor.putInt("random_seed", seed);
            editor.apply();
            activityGeneratingIntent();
        });
        rev.setOnClickListener(view -> {
            level = prefs.getInt("maze_size", level);
            builder = prefs.getString("builder_algorithm", builder);
            isPerfect = prefs.getBoolean("rooms_enabled", isPerfect);
            seed = prefs.getInt("random_seed", seed);
            Log.v("tag", "recognized data storage");
            activityGeneratingIntent();
        });
        levelval.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int retrieveProgress = seekBar.getProgress();
                level = retrieveProgress;
            }
        });
        perfectSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isPerfect = isChecked;
            }
        });
        build.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                builder = build.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        /*SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("maze_size", level);
        editor.putString("builder_algorithm", builder);
        editor.putBoolean("rooms_enabled", isPerfect);
        editor.putInt("random_seed", seed);
        editor.apply();*/
    }

    private void activityGeneratingIntent() {
        Intent in = new Intent(this, GeneratingActivity.class);
        in.putExtra("Level", level);
        in.putExtra("Rooms", isPerfect);
        in.putExtra("Builder", builder);
        in.putExtra("Seed", seed);
        Log.v("tag", "seed: " + seed);
        startActivity(in);
        overridePendingTransition(R.anim.transition_in, R.anim.transition_out);
    }

}