package com.example.recolor;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

public class Sprite {
    private Bitmap bitmap;
    private double speed;
    public double targetX;
    public double targetY;
    private List<Rect> frames;
    private int frameWidth;
    private int frameHeight;
    private int currentFrame;
    private double frameTime;
    private double timeForCurrentFrame;
    private double x;
    private double y;
    private double velocityX;
    private double velocityY;
    private int padding;

    public Sprite(double x, double y, double velocityX, double velocityY,  Rect initialFrame, Bitmap bitmap) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.bitmap = bitmap;
        this.frames = new ArrayList<Rect>();
        this.frames.add(initialFrame);
        this.bitmap = bitmap;
        this.timeForCurrentFrame = 0.0;
        this.frameTime = 0.1;
        this.currentFrame = 0;
        this.frameWidth = initialFrame.width();
        this.frameHeight = initialFrame.height();
        this.padding = 20;
    }
    public Sprite(double x, double y, Bitmap bitmap) {
        this.x = x;
        this.y = y;
        this.bitmap = bitmap;
        this.frames = new ArrayList<Rect>();
        this.frames.add(new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()));
        this.timeForCurrentFrame = 0.0;
        this.frameTime = 0.1;
        this.currentFrame = 0;
        this.padding = 20;
    }
    public void setX(double x) {
        this.x = x;
    }
    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }
    public int getFrameWidth() {
        return frameWidth;
    }
    public void setFrameWidth(int frameWidth) {
        this.frameWidth = frameWidth;
    }
    public int getFrameHeight() {
        return frameHeight;
    }
    public void setFrameHeight(int frameHeight) {
        this.frameHeight = frameHeight;
    }
    public double getVx() {
        return velocityX;
    }
    public void setVx(double velocityX) {
        this.velocityX = velocityX;
    }
    public double getVy() {
        return velocityY;
    }
    public void setVy(double velocityY) {
        this.velocityY = velocityY;
    }
    public int getCurrentFrame() {
        return currentFrame;
    }
    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame%frames.size();
    }
    public double getFrameTime() {
        return frameTime;
    }
    public void setFrameTime(double frameTime) {
        this.frameTime = Math.abs(frameTime);
    }
    public double getTimeForCurrentFrame() {
        return timeForCurrentFrame;
    }
    public void setTimeForCurrentFrame(double timeForCurrentFrame) {
        this.timeForCurrentFrame = Math.abs(timeForCurrentFrame);
    }
    public int getFramesCount () {
        return frames.size();
    }
    public void update (int ms) {
        moveToTarget();
    }
    private void moveToTarget(){
        if (x < targetX){
            x += Math.min(speed, targetX - x);
        }else if (x > targetX){
            x -= Math.min(speed, x - targetX);
        }

        if (y < targetY){
            y += Math.min(speed, targetY - y);
        }else if (y > targetY){
            y -= Math.min(speed, y - targetY);
        }
    }
    public void move(double x, double y){
        this.x = x;
        this.y = y;
    }
    public void tweenPos(double x, double y, double speed){
        this.targetX = x;
        this.targetY = y;
        this.speed = speed;
    }
    public void draw (Canvas canvas) {
        Paint p = new Paint();
        Rect destination = new Rect((int)x, (int)y, (int)(x + frameWidth), (int)(y + frameHeight));
        canvas.drawBitmap(bitmap, frames.get(currentFrame), destination,  p);
    }
    public Rect getBoundingBoxRect () {
        return new Rect((int)x+padding, (int)y+padding, (int)(x + frameWidth - 2 * padding), (int)(y + frameHeight - 2 * padding));
    }
    public boolean intersect (Sprite s) {
        return getBoundingBoxRect().intersect(s.getBoundingBoxRect());
    }
    public void setSpeed(double speed){
        this.speed = speed;
    }
}
