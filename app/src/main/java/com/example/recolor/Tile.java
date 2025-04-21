package com.example.recolor;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Tile extends Sprite {
    String type;
    int color;
    static class COLOR{
        static final int YELLOW = 0;
        static final int GREEN = 1;
        static final int RED = 2;
        static final int BLUE = 3;
    }

    public Tile(double x, double y, double velocityX, double velocityY, Rect initialFrame, Bitmap bitmap, String type) {
        super(x, y, velocityX, velocityY, initialFrame, bitmap);
        this.type = type;
    }
    public Tile(double x, double y, Bitmap bitmap) {
        super(x, y, bitmap);
        this.type = "None";
    }
    public Tile(double x, double y, Bitmap bitmap, String type) {
        super(x, y, bitmap);
        this.type = type;
    }

    public void setColor(int color){
        this.color = color;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public int getColor() {
        return color;
    }
}
