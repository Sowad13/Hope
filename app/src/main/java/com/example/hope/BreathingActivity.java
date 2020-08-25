package com.example.hope;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class BreathingActivity extends AppCompatActivity implements NumberDialogue.NumberDialogueListener {

    MediaPlayer player;
    FloatingActionButton pause,stop,play;
    ProgressBar progressBar;
    TextView timeDisplay;
    long mins,secs;
    long millTime,fimmil,temp=0,startTime;
    CountDownTimer countDownTimer;

    int progress=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breathing);

        progressBar = findViewById(R.id.progressBar_bar);
        timeDisplay = findViewById(R.id.timeDisplay);
        play = findViewById(R.id.timerPlay);
        pause = findViewById(R.id.timerPause);
        stop = findViewById(R.id.timerStop);

        OpenDialogue();

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause.setVisibility(View.INVISIBLE);
                play.setVisibility(View.VISIBLE);
                Pause();


            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pause.setVisibility(View.VISIBLE);
                play.setVisibility(View.INVISIBLE);

                StartTimer();

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stop();

            }
        });




    }

    private void StartTimer() {


        if(player==null)
        {
            player = MediaPlayer.create(this,R.raw.exercise);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
        }

        player.start();


        countDownTimer = new CountDownTimer(millTime,1000) {
            @Override
            public void onTick(long millisUntilFinished) {


                UpdateTimer();
                millTime = millisUntilFinished;
                temp = startTime-millTime;
                progress = (int) ((100.0/fimmil)*temp);
                progressBar.setProgress(progress);





            }

            @Override
            public void onFinish() {
                UpdateTimer();

                progressBar.setProgress(100);
                stopPlayer();


            }
        }.start();


    }

    private void UpdateTimer() {

        int min = (int) (millTime/1000)/60;
        int sec = (int) (millTime/1000)%60;


        String timeleft = String.format(Locale.getDefault(),"%02d : %02d",min,sec);

        timeDisplay.setText(timeleft);


    }

    private void OpenDialogue() {

        NumberDialogue numberDialogue = new NumberDialogue();

        numberDialogue.show(getSupportFragmentManager(),"NumberDialogue");

        numberDialogue.setCancelable(false);
    }

    @Override
    public void applyTexts(int min, int sec) {

        mins = min;
        secs = sec;
        millTime =  mins*60000 + secs*1000;
        startTime = millTime;
        progress = 0;
        progressBar.setProgress(progress);
        UpdateTimer();
        fimmil = millTime;
        StartTimer();


    }


    void Pause()
    {

        if(player!=null)
        {
            player.pause();
        }
        countDownTimer.cancel();

    }

    void Stop()
        {

        countDownTimer.cancel();
        progress =0;
        progressBar.setProgress(progress);
        mins = 0;
        secs = 0;
        temp =0;
        UpdateTimer();
        OpenDialogue();
        stopPlayer();



    }

    void stopPlayer()
    {
        if(player!=null)
        {
            player.release();
            player = null;
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }
}
