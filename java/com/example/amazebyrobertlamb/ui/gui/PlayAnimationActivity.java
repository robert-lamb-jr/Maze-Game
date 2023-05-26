package com.example.amazebyrobertlamb.ui.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import com.example.amazebyrobertlamb.R;
import com.example.amazebyrobertlamb.ui.generation.Maze;

public class PlayAnimationActivity extends AppCompatActivity implements View.OnClickListener {

    private Button toWinning;
    private Button toLosing;
    private Button back2title;
    private ImageButton zoomIn;
    private ImageButton zoomOut;
    private ToggleButton showMaze;
    private SeekBar animationSpeed;
    private int speedVal;
    private ImageButton playButton;
    private ImageButton pauseButton;
    private Button forwardSensor;
    private Button backwardSensor;
    private Button leftSensor;
    private Button rightSensor;
    private ProgressBar progressBar;
    private MazePanel panel;
    private boolean isPlaying;
    //private static boolean fWorking;
    //private static boolean bWorking;
    //private static boolean lWorking;
    //private static boolean rWorking;
    private Robot robot;
    private RobotDriver driver;
    private int pathLength;
    private int minSteps;
    private float energyConsump;
    private Maze maze;
    private StatePlaying state;
    private String driverString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playanimation);
        panel = (MazePanel) findViewById(R.id.mazePanelAuto);
        maze = GeneratingActivity.getMaze();
        state = new StatePlaying();
        //state.setMaze(Constants.currentMaze);
        state.setMaze(maze);
        //state.start(panel);
        robot = new ReliableRobot(state);
        minSteps = maze.getDistanceToExit(maze.getStartingPosition()[0], maze.getStartingPosition()[1]);
        //Bundle extraIntes = getIntent().getExtras();
        driverString = GeneratingActivity.getDriver();
        //String c = extraIntes.getString("RobotConfig");
        /*if(c == "Premium") {
            fWorking = true;
            bWorking = true;
            lWorking = true;
            rWorking = true;
            robot = new ReliableRobot(state, fWorking, bWorking, lWorking, rWorking);
        }
        else if(c == "Mediocre") {
            fWorking = true;
            bWorking = true;
            lWorking = false;
            rWorking = false;
            robot = new UnreliableRobot(state, fWorking, bWorking, lWorking, rWorking);
        }
        else if(c == "Soso") {
            fWorking = false;
            bWorking = false;
            lWorking = true;
            rWorking = true;
            robot = new UnreliableRobot(state, fWorking, bWorking, lWorking, rWorking);
        }
        else if(c == "Shaky") {
            fWorking = false;
            bWorking = false;
            lWorking = false;
            rWorking = false;
            robot = new UnreliableRobot(state, fWorking, bWorking, lWorking, rWorking);
        }*/
        if(driverString == "Wizard") {
            driver = new Wizard();
            driver.setRobot(robot);
            driver.setMaze(maze);
            Log.v("wizard", "actually picked wizard");
        }
        else if(driverString == "WallFollower") {
            driver = new SmartWallFollower();
            driver.setRobot(robot);
            driver.setMaze(maze);
            Log.v("wallfol", "actually picked wallfollower");
        }
        isPlaying = true;
        forwardSensor = (Button) findViewById(R.id.forwardSensor);
        backwardSensor = (Button) findViewById(R.id.backwardSensor);
        leftSensor = (Button) findViewById(R.id.leftSensor);
        rightSensor = (Button) findViewById(R.id.rightSensor);
        zoomIn = (ImageButton) findViewById(R.id.zoomIn2);
        zoomOut = (ImageButton) findViewById(R.id.zoomOut2);
        playButton = (ImageButton) findViewById(R.id.playButton);
        pauseButton = (ImageButton) findViewById(R.id.pauseButton);
        zoomIn.setOnClickListener(this);
        zoomOut.setOnClickListener(this);
        playButton.setOnClickListener(this);
        pauseButton.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        minSteps = maze.getDistanceToExit(maze.getStartingPosition()[0], maze.getStartingPosition()[1]);
        energyConsump = 0;
        pathLength = 0;
        showMaze = (ToggleButton) findViewById(R.id.showMaze2);
        showMaze.setOnClickListener(this);
        animationSpeed = (SeekBar) findViewById(R.id.animationSpeed);
        speedVal = 1000;
        animationSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progress = 1;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                speedVal = 1000 - ((seekBar.getProgress() - 1) * 200);
                progress = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                speedVal = 1000 - ((seekBar.getProgress() - 1) * 200);

            }


        });
        state.start(panel);
        onStart();

        toWinning = (Button)findViewById(R.id.go2winning);
        toLosing = (Button)findViewById(R.id.go2losing);
        toWinning.setOnClickListener(view -> {
            activityWinningIntent();
        });
        toLosing.setOnClickListener(view -> {
            activityLosingIntent();
        });
        back2title = (Button)findViewById(R.id.back2title2);
        back2title.setOnClickListener(view -> {
            activityTitleIntent();
        });
    }

    private void activityWinningIntent() {
        Intent in = new Intent(this, WinningActivity.class);
        in.putExtra("pathLength", pathLength);
        in.putExtra("energyConsumption", energyConsump);
        in.putExtra("minSteps", minSteps);
        startActivity(in);
        overridePendingTransition(R.anim.transition_in, R.anim.transition_out);
    }

    private void activityLosingIntent() {
        Intent in = new Intent(this, LosingActivity.class);
        in.putExtra("pathLength", pathLength);
        in.putExtra("energyConsumption", energyConsump);
        in.putExtra("minSteps", minSteps);
        startActivity(in);
        overridePendingTransition(R.anim.transition_in, R.anim.transition_out);
    }
    private void activityTitleIntent() {
        Intent in = new Intent(this, AMazeActivity.class);
        startActivity(in);
        overridePendingTransition(R.anim.transition_in, R.anim.transition_out);
    }

    /*public void changeSensor(String dir){
        if (dir == "forward"){
            if(fWorking) {
                forwardSensor.setBackgroundColor(Color.RED);
                fWorking = false;
            }
            else{
                forwardSensor.setBackgroundColor(Color.GREEN);
                fWorking = true;
            }
        }
        if (dir=="back"){
            if(bWorking) {
                backwardSensor.setBackgroundColor(Color.RED);
                bWorking = false;
            }
            else{
                backwardSensor.setBackgroundColor(Color.GREEN);
                bWorking = true;
            }
        }
        if (dir=="left"){
            if(lWorking) {
                leftSensor.setBackgroundColor(Color.RED);
                lWorking = false;
            }
            else{
                leftSensor.setBackgroundColor(Color.GREEN);
                lWorking = true;
            }
        }
        if (dir=="right"){
            if(rWorking) {
                rightSensor.setBackgroundColor(Color.RED);
                rWorking = false;
            }
            else{
                rightSensor.setBackgroundColor(Color.GREEN);
                rWorking = true;
            }
        }
    }*/
    @Override
    public void onClick(View view) {
        Constants.UserInput uikey = null;
        switch (view.getId()) {
            case R.id.zoomIn2:
                uikey = Constants.UserInput.ZOOMIN;
                state.handleUserInput(uikey, 1);
                break;
            case R.id.zoomOut2:
                uikey = Constants.UserInput.ZOOMOUT;
                state.handleUserInput(uikey, 1);
                break;
            case R.id.playButton:
                if (!isPlaying) {
                    isPlaying = true;
                    onStart();
                }
                break;
            case R.id.pauseButton:
                if (isPlaying) {
                    isPlaying = false;
                }
                break;
            case R.id.showMaze2:
                uikey = Constants.UserInput.TOGGLELOCALMAP;
                state.handleUserInput(uikey, 1);
                break;
        }
    }

    public void setProgressBar(float energy){
        energyConsump = energy;
        progressBar.setProgress((int)(energy));
    }

    private void passOn(String win){
        if (win == "win") {
            Intent i = new Intent(PlayAnimationActivity.this, WinningActivity.class);
            i.putExtra("pathLength", pathLength);
            i.putExtra("energyConsumption", energyConsump);
            i.putExtra("minSteps", minSteps);
            startActivity(i);
            overridePendingTransition(R.anim.transition_in, R.anim.transition_out);
        }
        else{
            Intent i = new Intent(PlayAnimationActivity.this, LosingActivity.class);
            i.putExtra("pathLength", pathLength);
            i.putExtra("energyConsumption", energyConsump);
            i.putExtra("minSteps", minSteps);
            startActivity(i);
            overridePendingTransition(R.anim.transition_in, R.anim.transition_out);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(!robot.isAtExit()) {
                        Log.v("tag", "driving");
                        driver.drive2Exit();
                        progressBar.setProgress((int)(3500 - energyConsump));
                        //Log.v("tag", "coords1:" + GeneratingActivity.getMaze().getStartingPosition()[0] + GeneratingActivity.getMaze().getStartingPosition()[1]);
                        //Log.v("tag", "coords2:" + robot.getMaze().getStartingPosition()[0] + robot.getMaze().getStartingPosition()[1]);
                        Log.v("tag", "drove");
                    }
                    if(robot.isAtExit()) {
                        robot.move(1);
                        energyConsump = 3500 - robot.getBatteryLevel();
                        pathLength = robot.getOdometerReading();
                        activityWinningIntent();
                    }
                    else if(robot.getBatteryLevel() <= 0 || robot.hasStopped()) {
                        energyConsump = 3500 - robot.getBatteryLevel();
                        pathLength = robot.getOdometerReading();
                        activityLosingIntent();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}