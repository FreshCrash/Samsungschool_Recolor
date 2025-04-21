package com.example.recolor;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.opencsv.CSVReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class GameView extends View {

    private static final Log log = LogFactory.getLog(GameView.class);
    private int viewWidth;
    private int viewHeight;
    float x1, y1, x2, y2;
    int playerx, playery;
    Player player;
    Tile[][] field;
    public GameView(Context context) {
        super(context);
        try {
            loadLevel(R.raw.test_level);
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
    void loadLevel(int resfile) throws IOException {
        InputStream inputStream = getResources().openRawResource(resfile);
        CSVFile csvFile = new CSVFile(inputStream);
        List<String[]> fieldStr = csvFile.read();
        log.info(fieldStr.get(0).toString());
        for(int ri = 0; ri < fieldStr.size(); ri++){
            String[] row = fieldStr.get(ri);
            for(int j = 0; j < row.length; j++){
                String elem = row[j];
                String k1;
                String k2;
                int underscoreid = elem.indexOf('_');
                if(underscoreid < 0){
                    k1 = elem;
                }else {
                    k1 = elem.substring(0, underscoreid);
                    k2 = elem.substring(underscoreid);
                }
                if(k1.equals("Block")){
                    field[ri][j] = new Tile(ri, j, BitmapFactory.decodeResource(getResources(), R.drawable.block), "Block");
                }else if (k1.equals("Player")){
                    player = new Player(0, 0, BitmapFactory.decodeResource(getResources(), R.drawable.player), Tile.COLOR.YELLOW, ri, j);
                    field[ri][j] = new Tile(ri, j, BitmapFactory.decodeResource(getResources(), R.drawable.block));
                }
            }
        }
    }
}
