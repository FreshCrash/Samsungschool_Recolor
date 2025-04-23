package com.example.recolor;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Tile extends Sprite {
    String type;
    int tileX, tileY;
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
    public int getTileX() {
        return tileX;
    }

    public void setTileX(int tileX) {
        this.tileX = tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
    }
    public void SetTilePos(int tileX, int tileY){
        this.tileX = tileX;
        this.tileY = tileY;
    }
    public void SetTargetByTilePos(int tileSize, int offsetX, int offsetY){
        this.targetX = offsetX + (tileSize * tileX);
        this.targetY = offsetY + (tileSize * tileY);
    }
}
