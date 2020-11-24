package com.sro.io;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.System.exit;

public class MainActivity extends AppCompatActivity {

    ImageView left, ball, right;
    int screenWidth, screenHeight;
    float ballX, ballY;
    RelativeLayout relativeLayout;
    public Timer timer;
    TextView user, ai, announcement, tap;
    int userScore, aiScore;
    boolean gameStarted = false;
    long TIMES = 4000;
    int applause;
    int aww;
    int click;
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
        setContentView(R.layout.activity_main);
        timer = new Timer();



        left = findViewById(R.id.leftHandle);
        ball = findViewById(R.id.ball);
        right = findViewById(R.id.rightHandle);
        relativeLayout = findViewById(R.id.myLayout);
        user = findViewById(R.id.player1);
        ai = findViewById(R.id.player2);
        announcement = findViewById(R.id.announce);
        tap = findViewById(R.id.startGame);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                .build();
        applause = soundPool.load(this, R.raw.applause, 1);
        aww = soundPool.load(this, R.raw.aww, 1);
        click = soundPool.load(this, R.raw.click, 1);
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
        userScore = 0;
        aiScore = 0;


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
            if (ballX > screenWidth) {

                userScore++;
                soundPool.play(points, 1, 1, 0, 0, 1);

                ballX = screenWidth / 2f;
                ballY = screenHeight / 2f;
                ballXSpeed = RandomDir()*10;
                ballYSpeed = RandomDir()*4;

                String str = String.valueOf(userScore);
                user.setText(str);


            }
            if (ballX < 0) {
                soundPool.play(points, 1, 1, 0, 0, 1);
                ballXSpeed = RandomDir()*10;
                ballYSpeed = RandomDir()*4;
                ballX = screenWidth / 2f;
                ballY = screenHeight / 2f;
                aiScore++;
                String str1 = String.valueOf(aiScore);
                ai.setText(str1);

            }

            if (ballY > 1000)
                ballYSpeed = -ballYSpeed;
            if (ballY < 30)
                ballYSpeed = -ballYSpeed;


            if (ballX < (left.getX() + left.getWidth())) {
                if (ballY >= (left.getY() - 30) && ballY <= ((left.getY() + left.getHeight()) + 30)) {
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
            if (userScore == 4 || aiScore == 4) {
                if (userScore > aiScore) {
                    announcement.setText("You Won");
                    soundPool.play(applause, 1, 1, 0, 0, 1);

                    try {
                        Thread.sleep(TIMES);
                        startActivity(new Intent(this, MainMenu.class));
                        finish();
                        System.exit(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                } else {
                    announcement.setText("AI Won");
                    soundPool.play(aww, 1, 1, 0, 0, 1);
                    try {

                        Thread.sleep(TIMES);
                        startActivity(new Intent(this, MainMenu.class));
                        finish();
                        System.exit(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            }


            ball.setX(ballX);
            ball.setY(ballY);

//          IMPOSSIBLE GAME
//        if (ballY > 50 && ballY < 800)
//            right.setY(ballY - 40);


            if (ballY > right.getY()) {
                rightY += 15;
            }
            if (ballY < (right.getY() + right.getHeight())) {
                rightY -= 15;
            }
            right.setY(rightY);
        }
    }
}