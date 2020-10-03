package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button stop_music;
    AudioManager mAudioManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stop_music = findViewById(R.id.stop_music);
        stop_music.setOnClickListener(stopMusic);
    }

    public View.OnClickListener stopMusic = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
            if (mAudioManager.isMusicActive()) {
                mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
            }
            System.exit(0);
        }
    };
}
