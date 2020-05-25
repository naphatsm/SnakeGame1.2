package com.naphat.gamegame;

import androidx.appcompat.app.AppCompatActivity;

import android.drm.DrmStore;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.naphat.gamegame.enums.Direction;
import com.naphat.gamegame.enums.GameState;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private GameEngine gameEngine;
    private SnakeView snakeView;

    private final Handler hanlder = new Handler();
    private final int updateDelay = 125;

    private float prevX,prevY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameEngine = new GameEngine();
        gameEngine.initGame();
        snakeView = (SnakeView) findViewById(R.id.snakeView);
        snakeView.setOnTouchListener(this);
        startUpdateHanlder();

    }
    public void startUpdateHanlder(){
        hanlder.postDelayed(new Runnable() {
            @Override
            public void run() {
                gameEngine.Update();
                if(gameEngine.getCurrentGameState() == GameState.Running){
                    hanlder.postDelayed(this,updateDelay);
                }
                if(gameEngine.getCurrentGameState() == GameState.Lost){
                    onGameLost();
                }
                snakeView.setSnakeViewMap(gameEngine.getMap(),gameEngine.getScore());
                snakeView.invalidate();
            }
        },updateDelay);
    }

    private void onGameLost() {
        gameEngine = new GameEngine();
        gameEngine.initGame();
        startUpdateHanlder();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                prevX = event.getX();
                prevY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                float newX = event.getX();
                float newY = event.getY();

                if(Math.abs(newX - prevX) > Math.abs(newY - prevY)){
                    if(prevX > newX){
                        gameEngine.UpdateDirection(Direction.West);
                    }
                    else gameEngine.UpdateDirection(Direction.East);
                }
                else {
                    if(prevY > newY){
                        gameEngine.UpdateDirection(Direction.North);
                    }
                    else gameEngine.UpdateDirection(Direction.South);
                }

        }
        return true;
    }
}
