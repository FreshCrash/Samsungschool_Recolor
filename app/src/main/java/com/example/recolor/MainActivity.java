package com.example.recolor;

import android.media.metrics.Event;
import android.os.Bundle;
import android.view.MotionEvent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    GestureDetector gestureDetector;

    float x1, y1, x2, y2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        this.gestureDetector = new GestureDetector(MainActivity.this, this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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

                        }
                    }
                    else {

                    }
                }
        }
        return false;
    }
}