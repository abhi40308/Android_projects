package com.example.abhijeetsinghkhangarot.timers;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView timerTextView;
    Button controllerButton;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    public  void resetTimer(){

        timerSeekBar.setProgress(30);
        timerTextView.setText("00:30");
        countDownTimer.cancel();
        controllerButton.setText("GO!");
        counterIsActive = false;
        timerSeekBar.setEnabled(true);

    }

    public  void progressTimer(int progress){

        int minutes = (int) progress / 60;
        int seconds = progress - minutes * 60;

        String secondString = Integer.toString(seconds);
        String minuteString = Integer.toString(minutes);
        //System.out.println("secondString : " + secondString);

        if(seconds <= 9){
            secondString = "0" + secondString;
        }

        if(minutes <= 9){
            minuteString = "0" + minuteString;
        }

        timerTextView.setText(minuteString + ":" + secondString);

    }

    public void controlTimer(View view){

        if(counterIsActive == false) {
            controllerButton.setText("Stop!");
            counterIsActive = true;
            timerSeekBar.setEnabled(false);


            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {

                    int seconds = (int) millisUntilFinished / 1000;

                    progressTimer(seconds);

                }

                @Override
                public void onFinish() {

                    // Log.i("finished" , "hurrey");
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mplayer.start();
                    Toast.makeText(getApplicationContext(), "Timer complete!", Toast.LENGTH_LONG).show();
                    resetTimer();

                }
            }.start();
        }
        else{

            resetTimer();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        timerSeekBar = (SeekBar)findViewById(R.id.timerSeekBar);
        timerTextView = (TextView)findViewById(R.id.timerTextView);
        controllerButton = (Button)findViewById(R.id.controllerButton);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                progressTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

//        final Handler handler = new Handler();
//        Runnable run = new Runnable() {
//            @Override
//            public void run() {
//
//                Log.i("Runnable running", "Every 1 second");
//                handler.postDelayed(this, 1000);
//
//            }
//        };
//
//        handler.post(run);
    }
}
