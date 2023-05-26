package com.example.amazebyrobertlamb.ui.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.amazebyrobertlamb.R;
import com.example.amazebyrobertlamb.ui.gui.AMazeActivity;

import org.w3c.dom.Text;

public class WinningActivity extends AppCompatActivity {

    private Button return2screen;
    private TextView pathLength;
    private TextView minPath;
    private TextView energyConsump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winning);
        pathLength = (TextView) findViewById(R.id.pathLenVal);
        minPath = (TextView) findViewById(R.id.minLenVal);
        energyConsump = (TextView) findViewById(R.id.energyConsumpVal);
        Bundle extraIntes = getIntent().getExtras();
        int retPathLength = extraIntes.getInt("pathLength");
        int retMinPath = extraIntes.getInt("minSteps");
        float retEnergyConsump = extraIntes.getFloat("energyConsumption");
        pathLength.setText(Integer.toString(retPathLength));
        minPath.setText(Integer.toString(retMinPath));
        energyConsump.setText(Float.toString(retEnergyConsump));
        return2screen = (Button)findViewById(R.id.button);
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.win);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.v("tag", "pretty sure it played");
                mediaPlayer.release();
            }
        });
        mediaPlayer.start();
        return2screen.setOnClickListener(view -> {
            activityTitleIntent();
        });
    }

    public void activityTitleIntent() {
        Intent in = new Intent(getApplicationContext(), AMazeActivity.class);
        startActivity(in);
        overridePendingTransition(R.anim.transition_in, R.anim.transition_out);
    }
}