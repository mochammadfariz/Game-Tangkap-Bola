package com.oreon.burgerman;


import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    private BurgerManView objectV;
    private Handler handler = new Handler();
    private final static long Interval = 30;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MediaPlayer musik = MediaPlayer.create(MainActivity.this,R.raw.elevatorfix);
        musik.start();
        super.onCreate(savedInstanceState);
       objectV = new BurgerManView(this);
       setContentView(objectV);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run()
            {
                handler.post(new Runnable() {
                    @Override
                    public void run()
                    {
                        objectV.invalidate();
                    }
                });
            }
        },0,Interval);
    }


}
