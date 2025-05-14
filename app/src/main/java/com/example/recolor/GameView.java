package com.example.recolor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class GameView extends View {

    private static final Log log = LogFactory.getLog(GameView.class);
    private int viewWidth;
    private int totalSensors, activatedSensors;
    final int tileWidth = 90;
    private int viewHeight;
    private int updateInterval = 30;
    float x1, y1, x2, y2;
    int playerx, playery;
    Player player;
    Tile[][] field;
    public GameView(Context context) {
        super(context);
        totalSensors = 0;
        activatedSensors = 0;
        try {
            loadLevel(R.raw.test_level);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Timer t = new Timer();
        t.start();
    }

    protected void update () {
        if (player != null){
            player.update(updateInterval);
        }
        invalidate();
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
//        canvas.drawARGB(250, 255, 255, 255);
        if (player != null){
            player.draw(canvas);
//            log.debug("Player updated");
        }
        for(Tile[] row: field){
            for(Tile tile: row){
                if(tile != null){
                    tile.draw(canvas);
                }
            }
        }
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setTextSize(120.0f);
        p.setColor(Color.WHITE);
        canvas.drawText(activatedSensors+"/"+totalSensors, viewWidth / 2, 70, p);

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
                            movePlayer(-1, 0);
                        }
                        else {
                            Toast.makeText(this.getContext(), "Right", Toast.LENGTH_SHORT).show();
                            movePlayer(1, 0);
                        }
                    }
                    else {
                        if(y1 - y2 > 0){
                            Toast.makeText(this.getContext(), "Up", Toast.LENGTH_SHORT).show();
                            movePlayer(0, -1);
                        }
                        else {
                            Toast.makeText(this.getContext(), "Down", Toast.LENGTH_SHORT).show();
                            movePlayer(0, 1);
                        }
                    }
                }
        }
        return true;
    }
    private void movePlayer(int dx, int dy){
        if(!field[playerx + dx][playery + dy].type.equals("Block")){
            playerx += dx;
            playery += dy;
            if(player != null){
                player.SetTilePos(playerx, playery);
            }
            Tile currTile = field[playerx][playery];
            if(currTile.type.equals("ColorBlock")){
                if(currTile.type.equals("Sensor")){
                    if(currTile.getColor() != player.getColor()){
                        if(currTile.targetColor == player.getColor()){
                            activatedSensors += 1;
                        }
                        else{
                            activatedSensors -= 1;
                        }
                        if(activatedSensors == totalSensors){
                            log.info("Level completed");
                        }
                    }
                }
            }
            currTile.setColor(player.getColor());
            movePlayer(dx, dy);
        }
    }
    class Timer extends CountDownTimer {
        public Timer() {
            super(Integer.MAX_VALUE, updateInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            update();
        }
        @Override
        public void onFinish() {
        }
    }
    void loadLevel(int resfile) throws IOException {
        InputStream inputStream = getResources().openRawResource(resfile);
        CSVFile csvFile = new CSVFile(inputStream);
        List<String[]> fieldStr = csvFile.read();
        int sizey = fieldStr.size();
        int sizex = fieldStr.get(0).length;
        field = new Tile[sizey][sizex];
        for(int i = 0; i < fieldStr.size(); i++){
            String[] row = fieldStr.get(i);
            for(int j = 0; j < row.length; j++){
                String elem = row[j];
                String blockType;
                String blockColor = "";
                int underscoreid = elem.indexOf('_');
                if(underscoreid < 0){
                    blockType = elem;
                }else {
                    blockType = elem.substring(0, underscoreid);
                    blockColor = elem.substring(underscoreid);
                }
                if(blockType.equals("Block")){
                    field[i][j] = new Tile(tileWidth * i, tileWidth * j, BitmapFactory.decodeResource(getResources(), R.drawable.block), "Block");
                }else if (blockType.equals("Player")){
                    log.info("Player loaded");
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player);
                    log.info(bitmap.getHeight());
                    player = new Player(tileWidth * i, tileWidth * j, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), bitmap, Tile.COLOR.YELLOW, i, j);
                    //field[i][j] = new Tile(tileWidth * i, tileWidth * j, BitmapFactory.decodeResource(getResources(), R.drawable.block));

                }else if (blockType.equals("Detector")){
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.colorblock);
                    totalSensors += 1;
                    field[i][j] = new Tile(tileWidth * i, tileWidth * j, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), bitmap, "Sensor");
                    if(blockColor.equals("Yellow")){
                        field[i][j].targetColor = Tile.COLOR.YELLOW;
                    }
                }
            }
        }
    }
}
