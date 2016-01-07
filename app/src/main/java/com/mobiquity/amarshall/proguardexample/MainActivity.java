package com.mobiquity.amarshall.proguardexample;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button crashButton, discoButton;
    LinearLayout mainLayout;

    Handler discoHandler;
    Runnable discoRunnable;

    boolean isDiscoing;

    public static final int DISCO_SPEED_MILLISECONDS = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crashButton = (Button) findViewById(R.id.crashButton);
        discoButton = (Button) findViewById(R.id.discoButton);
        mainLayout = (LinearLayout) findViewById(R.id.MainActivityBackground);

        crashButton.setOnClickListener(new CrashAndBurnListener());
        discoButton.setOnClickListener(new DiscoListener());

        discoHandler = new Handler();

    }

    private class CrashAndBurnListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            int i = 0;
            int explosion = 5 / i;
        }
    }

    private class DiscoListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            if (isDiscoing) {
                stopTheDisco();
            } else {
                startTheDisco();
            }
        }
    }

    private void startTheDisco() {
        isDiscoing = true;

        setBackgroundToRandomColor();

        discoButton.setText("No Disco No");

        discoRunnable = new Runnable() {
            @Override
            public void run() {

                setBackgroundToRandomColor();

                discoHandler.postDelayed(discoRunnable, DISCO_SPEED_MILLISECONDS);
            }
        };

        discoHandler.postDelayed(discoRunnable, DISCO_SPEED_MILLISECONDS);
    }

    private void stopTheDisco() {
        isDiscoing = false;

        discoButton.setText("Go Disco Go");

        discoHandler.removeCallbacks(discoRunnable);

        setBackgroundColor(0xFFFFFFFF);

        //TODO: Make background white, Change button text
    }

    private void setBackgroundColor(int colorInHex) {
        mainLayout.setBackgroundColor(colorInHex);
    }

    private void setBackgroundToRandomColor() {
        Random rand = new Random();
        // Generates a random number between 0xFF000000 and 0xFFFFFFFF
        int randomHexValue = rand.nextInt(0x00FFFFFF) + 0xFF000000;
        Log.d("tag", "Random Hex Number: " + randomHexValue);
        setBackgroundColor(randomHexValue);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (discoHandler != null) {
            discoHandler.removeCallbacks(discoRunnable);
        }
    }
}
