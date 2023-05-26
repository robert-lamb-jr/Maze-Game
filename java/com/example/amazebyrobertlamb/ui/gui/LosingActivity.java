package com.example.amazebyrobertlamb.ui.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.TextView;

import com.example.amazebyrobertlamb.R;
import com.example.amazebyrobertlamb.ui.gui.AMazeActivity;

public class LosingActivity extends AppCompatActivity {

    private Button return2screen;
    private TextView pathLength;
    private TextView minPath;
    private TextView energyConsump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_losing);
        pathLength = (TextView) findViewById(R.id.pathLenVal2);
        minPath = (TextView) findViewById(R.id.minLenVal2);
        energyConsump = (TextView) findViewById(R.id.energyConsumpVal2);
        Bundle extraIntes = getIntent().getExtras();
        int retPathLength = extraIntes.getInt("pathLength");
        int retMinPath = extraIntes.getInt("minSteps");
        float retEnergyConsump = extraIntes.getFloat("energyConsumption");
        pathLength.setText(Integer.toString(retPathLength));
        minPath.setText(Integer.toString(retMinPath));
        energyConsump.setText(Float.toString(retEnergyConsump));
        return2screen = (Button)findViewById(R.id.button2);
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);
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