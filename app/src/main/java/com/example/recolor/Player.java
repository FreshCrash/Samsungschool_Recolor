package com.example.recolor;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Player extends Tile{
    int tileX, tileY, color;
    public Player(double x, double y, Rect initialFrame, Bitmap bitmap, int color, int tileX, int tileY) {
        super(x, y, 0, 0, initialFrame, bitmap, "Player");
        this.color = color;
        this.tileX = tileX;
        this.tileY = tileY;
        this.type = "Player";
    }

    public Player(double x, double y, Bitmap bitmap, int color, int tileX, int tileY) {
        super(x, y, bitmap);
        this.type = "Player";
        this.tileX = tileX;
        this.tileY = tileY;
        this.color = color;
    }
    @Override
    public void update(int ms){
        super.update(ms);
    }

}
