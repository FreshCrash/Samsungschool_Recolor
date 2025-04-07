package com.example.recolor;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class GameView extends View {

    private int viewWidth;
    private int viewHeight;
    float x1, y1, x2, y2;
    Tile[][] field;
    public GameView(Context context) {
        super(context);
        try {
            loadLevel("test_level.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();
                float magnitudeX = Math.abs(x1 - x2);
                float magnitudeY = Math.abs(y1 - y2);
                float totalMagnitude = (float) Math.sqrt(magnitudeX * magnitudeX + magnitudeY * magnitudeY);
                if(totalMagnitude >= 10){
                    if(magnitudeX > magnitudeY){
                        if(x1 - x2 > 0){
                            Toast.makeText(this.getContext(), "Left", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(this.getContext(), "Right", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        if(y1 - y2 > 0){
                            Toast.makeText(this.getContext(), "Up", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(this.getContext(), "Down", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        }
        return false;
    }
    void loadLevel(String filename) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(filename));
        List fieldStr = reader.readAll();
    }
}
