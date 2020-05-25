package com.naphat.gamegame;

import com.naphat.gamegame.enums.Direction;
import com.naphat.gamegame.enums.GameState;
import com.naphat.gamegame.enums.TileType;

import org.w3c.dom.ls.LSException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameEngine {
    public static final int GameWidth = 28;
    public static final int GameHeight = 41;

    private int score = 0;
    private List<Coordinate> walls = new ArrayList<>();
    private List<Coordinate> snake = new ArrayList<>();
    private List<Coordinate> apple = new ArrayList<>();
    private Direction currentDirection = Direction.East;
    private GameState currentGameState = GameState.Running;
    private Random random = new Random();
    private boolean increaseTail = false;

    public GameEngine(){

    }
    public void initGame(){
        AddWalls();
        AddSnake();
        AddApple();

    }

    public int getScore() {
        return score;
    }

    private void AddApple() {
        Coordinate coordinate = null;
        boolean added = false;
        while(!added){
            int x = 1 + random.nextInt(GameWidth-2);
            int y = 1 + random.nextInt(GameHeight-2);

            coordinate = new Coordinate(x,y);
            boolean collision = false;
            for(Coordinate s : snake){
                if(s.equals(coordinate)){
                    collision = true;
                }
            }
            added = !collision;
        }
        apple.add(coordinate);
    }

    public void Update(){
        switch (currentDirection) {
            case North:
                UpdateSnake(0,-1);
                break;
            case South:
                UpdateSnake(0,1);
                break;
            case East:
                UpdateSnake(1,0);
                break;
            case West:
                UpdateSnake(-1,0);
                break;
        }
        for(Coordinate w : walls){
            if(w.equals(snake.get(0))){
              currentGameState = GameState.Lost;
            }
        }
        for(int i =1; i< snake.size(); i++){

            if(snake.get(0).equals(snake.get(i))){
                currentGameState = GameState.Lost;
                return;
            }
        }
        Coordinate AppleToRemove = null;
        for(Coordinate a : apple){
            if(a.equals(snake.get(0))){
                AppleToRemove = a;
                increaseTail = true;
                score = score + snake.size();
            }
        }
        if(AppleToRemove != null){
            apple.remove(AppleToRemove);
            AddApple();
        }
    }

    public void UpdateDirection(Direction newDirection){
        if(Math.abs(newDirection.ordinal() - currentDirection.ordinal()) % 2 == 1){
            currentDirection = newDirection;
        }
    }

    public void UpdateSnake(int x,int y){
        int newX = snake.get(snake.size()-1).getX();
        int newY = snake.get(snake.size()-1).getY();

        for(int i = snake.size()-1; i>0; i--){
            snake.get(i).setX(snake.get(i-1).getX());
            snake.get(i).setY(snake.get(i-1).getY());
        }
        snake.get(0).setX(snake.get(0).getX()+x);
        snake.get(0).setY(snake.get(0).getY()+y);

        if(increaseTail){
            snake.add(new Coordinate(newX,newY));
            increaseTail = false;
        }
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    private void AddSnake() {
        snake.clear();

        snake.add(new Coordinate(7,7));
        snake.add(new Coordinate(6,7));
        snake.add(new Coordinate(5,7));
        snake.add(new Coordinate(4,7));
        snake.add(new Coordinate(3,7));
    }

    public TileType[][] getMap(){
        TileType[][] map = new TileType[GameWidth][GameHeight];

        for(int x=0; x< GameWidth-1; x++){
            for(int y =0; y<GameHeight-1; y++){
                map[x][y] = TileType.Nothing;
            }
        }

        for(Coordinate w : walls){
            map[w.getX()][w.getY()] = TileType.Wall;
        }

        for(Coordinate s : snake){
            map[s.getX()][s.getY()] = TileType.SnakeTail;
        }
        map[snake.get(0).getX()][snake.get(0).getY()] = TileType.SnakeHead;

        for(Coordinate a : apple){
            map[a.getX()][a.getY()] = TileType.Apple;
        }

        return map;
    }

    private void AddWalls() {
        for(int x = 0; x < GameWidth; x++){
            walls.add(new Coordinate(x,0));
            walls.add(new Coordinate(x,GameHeight-1));

        }
        for(int y = 0; y< GameHeight; y++){
            walls.add(new Coordinate(0,y));
            walls.add(new Coordinate(GameWidth-1,y));
        }
    }
}
