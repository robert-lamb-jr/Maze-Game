package com.example.amazebyrobertlamb.ui.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.amazebyrobertlamb.R;
import com.example.amazebyrobertlamb.ui.generation.MazeFactory;
import com.example.amazebyrobertlamb.ui.generation.Maze;
import com.example.amazebyrobertlamb.ui.generation.Order;
import com.example.amazebyrobertlamb.ui.generation.Order.Builder;

import java.util.Random;

public class GeneratingActivity extends AppCompatActivity implements Order, View.OnClickListener {

    private static boolean isPerfect;
    private String builder;
    private static int seed;
    private static int level;
    private ImageButton go2manual;
    private ImageButton go2wizard;
    private ImageButton go2wallfollower;
    private Button return2title;
    private ProgressBar progressBar;
    private MazeFactory mazeFactory;
    private static Builder genAlgorithm;
    private int percentGenerated;
    private static String mazeDriver;
    private static Maze maze;
    private boolean isRunning = false;
    private Spinner robotConfig;
    private String config;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {
            Log.d("Handler", "Message received: " + msg.what);
            progressBar.incrementProgressBy(5);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generating);
        go2manual = (ImageButton)findViewById(R.id.manualButton);
        go2wizard = (ImageButton)findViewById(R.id.wizardButton);
        go2wallfollower = (ImageButton)findViewById(R.id.wallfollowerButton);
        return2title = (Button)findViewById(R.id.ret2title);
        progressBar = findViewById(R.id.loadingBar);
        robotConfig = (Spinner) findViewById(R.id.robotConfig);
        robotConfig.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                config = parent.getItemAtPosition(position).toString();
                // Use the selected item
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
        /*new Thread(new Runnable() { //operates loading progress bar in generating
            @Override
            public void run() {
                for (int i = 0; i <= 100; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int finalI = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(finalI);
                        }
                    });
                }
            }
        }).start();*/

        Bundle extraIntes = getIntent().getExtras();
        isPerfect = extraIntes.getBoolean("Rooms");
        builder = extraIntes.getString("Builder");
        seed = extraIntes.getInt("Seed");
        level = extraIntes.getInt("Level");
        go2manual.setOnClickListener(this);
        go2wizard.setOnClickListener(this);
        go2wallfollower.setOnClickListener(this);
        return2title.setOnClickListener(this);
        generateMaze();
        percentGenerated = 0;
        mazeDriver = null;
    }

    private void generateMaze() {
        switch (builder){
            case "Prims":
                genAlgorithm = Builder.Prim;
                break;
            case "Boruvka":
                genAlgorithm = Builder.Boruvka;
                break;
            case "DFS":
                genAlgorithm = Builder.DFS;
                break;
            default:
                genAlgorithm = Builder.DFS;


        }
        Random rand = new Random();
        seed = rand.nextInt();

        mazeFactory = new MazeFactory();
        mazeFactory.order(this);

    }

    public void onStart() {
        super.onStart();
        progressBar.setProgress(0);
        Thread Loading = new Thread(new Runnable(){
            public void run() {
                try {
                    while (progressBar.getProgress() < 100 && isRunning) {
                        handler.sendMessage(handler.obtainMessage());
                        if (progressBar.getProgress() == 100 && mazeDriver == "Manual") {
                            progressBar.setProgress(100);
                            activityManualIntent();
                        } else if ((mazeDriver == "Wizard" || mazeDriver == "WallFollower") && progressBar.getProgress() == 100) {
                            progressBar.setProgress(100);
                            activityAutoIntent();
                        }
                    }
                } catch (Throwable t) {

                }

            }
        });
        isRunning = true;
        Loading.start();
    }

    public void onStop()
    {
        super.onStop();
        isRunning = false;
    }

    public void Continue() {
        if (progressBar.getProgress() == 100 && mazeDriver == "Manual"){
            progressBar.setProgress(100);
            activityManualIntent();
        }
        else if(progressBar.getProgress() == 100 && mazeDriver != null){
            progressBar.setProgress(100);
            activityAutoIntent();
        }
    }

    public void activityManualIntent() {
        Intent in = new Intent(this, PlayManuallyActivity.class);
        startActivity(in);
        overridePendingTransition(R.anim.transition_in, R.anim.transition_out);
    }
    public void activityAutoIntent() {
        Intent in = new Intent(this, PlayAnimationActivity.class);
        in.putExtra("RobotConfig", config);
        startActivity(in);
        overridePendingTransition(R.anim.transition_in, R.anim.transition_out);
    }
    public void activityTitleIntent() {
        Intent in = new Intent(this, AMazeActivity.class);
        startActivity(in);
        overridePendingTransition(R.anim.transition_in, R.anim.transition_out);
    }

    @Override
    public int getSkillLevel() {
        return level;
    }

    @Override
    public Builder getBuilder() {
        return genAlgorithm;
    }

    @Override
    public boolean isPerfect() {
        return isPerfect;
    }

    @Override
    public int getSeed() {
        return seed;
    }

    public static String getDriver() {
        return mazeDriver;
    }

    public static Maze getMaze() {
        return maze;
    }

    @Override
    public void deliver(Maze mazeConfig) {
        //Constants.currentMaze = mazeConfig;
        maze = mazeConfig;
    }

    @Override
    public void updateProgress(int percentage) {
        if (this.percentGenerated< percentage && percentage <= 100) {
            this.percentGenerated = percentage;
            progressBar.setProgress(getPercentGenerated());
        }
    }

    private int getPercentGenerated() {
        return percentGenerated;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.wizardButton:
                mazeDriver = "Wizard";
                break;
            case R.id.wallfollowerButton:
                mazeDriver = "WallFollower";
                break;
            case R.id.manualButton:
                mazeDriver = "Manual";
                break;
            case R.id.ret2title:
                activityTitleIntent();
        }
        Continue();
    }


}