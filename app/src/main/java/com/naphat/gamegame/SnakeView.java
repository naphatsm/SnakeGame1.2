package com.naphat.gamegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.naphat.gamegame.enums.TileType;

public class SnakeView extends View {
    private Paint mPaint = new Paint();
    private TileType[][] snakeViewMap;
    private int score;

    public SnakeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSnakeViewMap(TileType[][] map,int score){
        this.snakeViewMap = map;
        this.score = score;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float tileSizeX = canvas.getWidth()/snakeViewMap.length;
        float tileSizeY = canvas.getHeight()/(snakeViewMap[0].length+1);
        float circleSize = Math.min(tileSizeX,tileSizeY) /2;
        if(snakeViewMap != null){

            for(int x = 0; x<snakeViewMap.length; x++){
                for(int y= 0; y<snakeViewMap[x].length; y++){
                    switch (snakeViewMap[x][y]){
                        case Nothing:
                            mPaint.setColor(Color.WHITE);
                            break;
                        case Wall:
                            mPaint.setColor(Color.BLACK);
                            break;
                        case SnakeHead:
                            mPaint.setColor(Color.RED);
                            break;
                        case SnakeTail:
                            mPaint.setColor(Color.GREEN);
                            break;
                        case Apple:
                            mPaint.setColor(Color.RED);
                            break;
                    }
                    canvas.drawCircle(x * tileSizeX + tileSizeY / 2f +circleSize / 2, y * tileSizeY + tileSizeY / 2f + circleSize / 2 , circleSize , mPaint);
                }
            }
        }
        mPaint.setTextSize(50);
        canvas.drawText("socre = "+score,1 * tileSizeX + tileSizeY / 2f +circleSize / 2, 42 * tileSizeY + tileSizeY / 2f + circleSize / 2 -10, mPaint);
    }
}
