package com.hfad.stopwatch;

import android.app.Activity;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;


    Button startButton;
    Button stopButton;
    Button resetButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState!=null){
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("state");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();

        Log.d(TAG,"onCreate.....................................");

        startButton = (Button)findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = true;
            }
        });

        stopButton = (Button)findViewById(R.id.stop_button);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = false;
            }
        });
        resetButton = (Button)findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = false;
                seconds = 0;
            }
        });
    }

    private void runTimer(){
        final TextView timeViewer = (TextView)findViewById(R.id.time_view);


        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                //convert seconds to minutes and hours.
                int hours = seconds /3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;

                timeViewer.setText(String.format("%d:%02d:%02d",hours,minutes,secs));
                if (running){
                    seconds++;
                }
                handler.postDelayed(this,1000);
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG,"save state .................");
        outState.putInt("seconds",seconds);
        outState.putBoolean("state",running);
        outState.putBoolean("wasRunning",wasRunning);
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onRestart() {
        Log.d(TAG,"on restart .................");
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"on pause .................");
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"on resume.................");
        if (wasRunning){
            running=true;
        }
        Log.d(TAG,String.valueOf(running));
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG,"on destroyed .................");
        super.onDestroy();
    }
}
