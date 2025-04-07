package com.example.recolor;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Player extends Tile{
    int tileX, tileY, color;
    public Player(double x, double y, double velocityX, double velocityY, Rect initialFrame, Bitmap bitmap, int color) {
        super(x, y, velocityX, velocityY, initialFrame, bitmap);
        this.color = color;
    }

    public Player(double x, double y, Bitmap bitmap, int color, int tileX, int tileY) {
        super(x, y, bitmap);
        this.tileX = tileX;
        this.tileY = tileY;
        this.color = color;
    }

}
