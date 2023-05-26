package com.example.amazebyrobertlamb.ui.gui;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.ConnectionService;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.amazebyrobertlamb.R;
import com.example.amazebyrobertlamb.ui.generation.Maze;

public class PlayManuallyActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton leftButton;
    private ImageButton rightButton;
    private ImageButton upButton;
    private ImageButton downButton;
    private ImageButton zoomIn;
    private ImageButton zoomOut;
    private ToggleButton showMap;
    private ToggleButton showSolution;
    private ToggleButton showVisiWalls;
    private ImageButton jump;
    private int pathLength;
    private float energyConsumption;
    private Button shortcut;
    private Button back2title;
    private MazePanel panel;
    private Maze maze;
    private StatePlaying state;
    private int minSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playmanually);
        panel = findViewById(R.id.mazePanel);
        maze = GeneratingActivity.getMaze();
        state = new StatePlaying();
        //state.setMaze(Constants.currentMaze);
        state.setMaze(maze);
        state.start(panel);
        minSteps = maze.getDistanceToExit(maze.getStartingPosition()[0], maze.getStartingPosition()[1]);
        leftButton = (ImageButton)findViewById(R.id.leftButton);
        leftButton.setOnClickListener(this);
        rightButton = (ImageButton)findViewById(R.id.rightButton);
        rightButton.setOnClickListener(this);
        upButton = (ImageButton)findViewById(R.id.upButton);
        upButton.setOnClickListener(this);
        downButton = (ImageButton)findViewById(R.id.downButton);
        downButton.setOnClickListener(this);
        jump = (ImageButton) findViewById(R.id.jumpButton);
        jump.setOnClickListener(this);
        zoomIn = (ImageButton)findViewById(R.id.zoomIn);
        zoomIn.setOnClickListener(this);
        zoomOut = (ImageButton)findViewById(R.id.zoomOut);
        zoomOut.setOnClickListener(this);
        showMap = (ToggleButton)findViewById(R.id.showMaze);
        showMap.setOnClickListener(this);
        showSolution = (ToggleButton)findViewById(R.id.showSolution);
        showSolution.setOnClickListener(this);
        showVisiWalls = (ToggleButton)findViewById(R.id.showWalls);
        showVisiWalls.setOnClickListener(this);
        pathLength = 0;
        energyConsumption = 0;
        shortcut = (Button) findViewById(R.id.shortcut);
        shortcut.setOnClickListener(view -> {
            activityWinningIntent();
        });
        back2title = (Button) findViewById(R.id.back2title);
        back2title.setOnClickListener(view -> {
            activityTitleIntent();
        });
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                activityTitleIntent();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    private void activityWinningIntent() {
        Intent in = new Intent(this, WinningActivity.class);
        in.putExtra("minSteps", minSteps);
        in.putExtra("pathLength", pathLength);
        in.putExtra("energyConsumption", energyConsumption);
        startActivity(in);
        overridePendingTransition(R.anim.transition_in, R.anim.transition_out);
    }
    private void activityTitleIntent() {
        Intent in = new Intent(this, AMazeActivity.class);
        startActivity(in);
        overridePendingTransition(R.anim.transition_in, R.anim.transition_out);
    }



    @Override
    public void onClick(View view) {
        Context context = getApplicationContext();
        Constants.UserInput uikey = null;
        switch(view.getId()) {
            case R.id.upButton:
                pathLength++;
                energyConsumption++;
                uikey = Constants.UserInput.UP;
                state.handleUserInput(uikey, 1);
                break;
            case R.id.downButton:
                pathLength++;
                energyConsumption++;
                uikey = Constants.UserInput.DOWN;
                state.handleUserInput(uikey, 1);
                break;
            case R.id.leftButton:
                uikey = Constants.UserInput.LEFT;
                energyConsumption++;
                state.handleUserInput(uikey, 1);
                break;
            case R.id.rightButton:
                energyConsumption++;
                uikey = Constants.UserInput.RIGHT;
                state.handleUserInput(uikey, 1);
                break;
            case R.id.jumpButton:
                pathLength++;
                uikey = Constants.UserInput.JUMP;
                energyConsumption+=40;
                state.handleUserInput(uikey, 1);
            case R.id.zoomIn:
                uikey = Constants.UserInput.ZOOMIN;
                state.handleUserInput(uikey, 1);
                break;
            case R.id.zoomOut:
                uikey = Constants.UserInput.ZOOMOUT;
                state.handleUserInput(uikey, 1);
                break;
            case R.id.showMaze:
                uikey = Constants.UserInput.TOGGLELOCALMAP;
                state.handleUserInput(uikey, 1);
                break;
            case R.id.showSolution:
                uikey = Constants.UserInput.TOGGLESOLUTION;
                state.handleUserInput(uikey, 1);
                break;
            case R.id.showWalls:
                uikey = Constants.UserInput.TOGGLEFULLMAP;
                state.handleUserInput(uikey, 1);
                break;
        }
        if(state.isOutside(state.px, state.py)) {
            passOn("win");
        }
    }

    private void passOn(String win) {
        if(win == "win") {
            Intent in = new Intent(this, WinningActivity.class);
            in.putExtra("minSteps", minSteps);
            in.putExtra("pathLength", pathLength);
            in.putExtra("energyConsumption", energyConsumption);
            startActivity(in);
            overridePendingTransition(R.anim.transition_in, R.anim.transition_out);
        }
        else {
            Intent in = new Intent(this, LosingActivity.class);
            in.putExtra("minSteps", minSteps);
            in.putExtra("pathLength", pathLength);
            in.putExtra("energyConsumption", energyConsumption);
            startActivity(in);
            overridePendingTransition(R.anim.transition_in, R.anim.transition_out);
        }
    }
}