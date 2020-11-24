package com.sro.io;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

public class ImpossibleMode extends AppCompatActivity {

    ImageView left, ball, right;
    int screenWidth, screenHeight;
    float ballX, ballY;
    RelativeLayout relativeLayout;
    public Timer timer;
    TextView announcement,dscore, tap,Score;
    int score=0;
    boolean gameStarted = false;
    long TIMES = 4000;
    int applause;
    int hit;
    int points;
    float rightY;
    float point;
    private SoundPool soundPool;

    public int RandomDir() {
        int rand = (int) (Math.random() * 2);
        if (rand == 1)
            return 1;
        else return -1;
    }

    double ballXSpeed = 8 * RandomDir();
    double ballYSpeed = 2 * RandomDir();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impossible_mode);
        timer = new Timer();
        left = findViewById(R.id.leftHandlei);
        ball = findViewById(R.id.balli);
        Score = findViewById(R.id.scorei);
        dscore = findViewById(R.id.Scorei);
        right = findViewById(R.id.rightHandlei);
        relativeLayout = findViewById(R.id.myLayouti);
        announcement = findViewById(R.id.announcei);
        tap = findViewById(R.id.startGamei);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                .build();
        applause = soundPool.load(this, R.raw.applause, 1);
        hit = soundPool.load(this, R.raw.hit, 1);
        point = soundPool.load(this, R.raw.point, 1);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        ballX = screenWidth / 2f;
        ballY = screenHeight / 2f;
        rightY = screenHeight / 2f;
        left.setY(screenHeight / 2f);
        right.setY(screenHeight / 2f);


        relativeLayout.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float y = event.getY();
                tap.setVisibility(View.INVISIBLE);
                gameStarted = true;
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (y > 50 && y < 800) {
                        left.setY(y);
                    }
                }
                return true;
            }
        });

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                update();
            }
        }, 0, 20);
    }


    private void update() {


        if (gameStarted) {

            ballX -= ballXSpeed;
            ballY -= ballYSpeed;

            if (ballX < 0) {
                soundPool.play(points, 1, 1, 0, 0, 1);
                announcement.setVisibility(View.VISIBLE);
                dscore.setVisibility(View.VISIBLE);
                try {
                    sleep(TIMES);
                    startActivity(new Intent(getApplicationContext(),MainMenu.class));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }

            if (ballY > 1000)
                ballYSpeed = -ballYSpeed;
            if (ballY < 30)
                ballYSpeed = -ballYSpeed;


            if (ballX < (left.getX() + left.getWidth())) {
                if (ballY >= (left.getY() - 30) && ballY <= ((left.getY() + left.getHeight()) + 30)) {
                    score++;
                    String str2 = String.valueOf(score);
                    Score.setText(str2);
                    dscore.setText(str2);
                if (ballY >= left.getHeight() / 2f) {
                        ballXSpeed = -ballXSpeed;
                        ballXSpeed = ballXSpeed - 2;
                        ballYSpeed += 3;
                        soundPool.play(hit, 1, 1, 0, 0, 1);

                    }
                    if (ballY <= left.getHeight() / 2f) {
                        ballXSpeed = -ballXSpeed;
                        ballXSpeed = ballXSpeed - 2;
                        ballYSpeed -= 3;
                        soundPool.play(hit, 1, 1, 0, 0, 1);

                    }
                }

            }

            if (ballX > (right.getX() - 80)) {
                if (ballY >= (right.getY() - 20) && ballY <= ((right.getY() + right.getHeight()) + 20)) {
                    ballXSpeed = -ballXSpeed;
                    ballXSpeed += 2;
                    ballYSpeed -=3;
                    soundPool.play(hit, 1, 1, 0, 0, 1);

                }

            }


            ball.setX(ballX);
            ball.setY(ballY);

        if (ballY > 50 && ballY < 800)
            right.setY(ballY - 40);


//            if (ballY > right.getY()) {
//                rightY += 15;
//            }
//            if (ballY < (right.getY() + right.getHeight())) {
//                rightY -= 15;
//            }
            right.setY(rightY);
        }
    }
}
