package com.sro.io;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {
    TextView Playmenu, Setting, Exit;
    private SoundPool soundPool;
    int click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Playmenu = findViewById(R.id.play);
        Setting = findViewById(R.id.impossible);
        Exit = findViewById(R.id.exit);


        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                .build();
        click = soundPool.load(this, R.raw.click, 1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Playmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(click, 1, 1, 0, 0, 1);

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();

            }
        });
        Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(click, 1, 1, 0, 0, 1);
                startActivity(new Intent(getApplicationContext(), ImpossibleMode.class));
                finish();
            }
        });
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(click, 1, 1, 0, 0, 1);
                System.exit(0);
                finish();
            }
        });
    }
}
