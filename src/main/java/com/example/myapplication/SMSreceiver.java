package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsMessage;
import androidx.annotation.RequiresApi;
import static android.media.AudioManager.FLAG_SHOW_UI;

public class SMSreceiver extends BroadcastReceiver {
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String comparing_string = "message_tag";  //word which need to be detected
    AudioManager mAudioManager;
    MediaPlayer mp = null;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SMS_RECEIVED)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus.length == 0) {
                    return;
                }
                SmsMessage[] messages = new SmsMessage[pdus.length];
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    sb.append(messages[i].getMessageBody());
                }
                String message = sb.toString();
                if (message.contains(comparing_string) /*&& !getTopActivity(context).equals(MainActivity.class)*/) {
                    if (mp != null && mp.isPlaying()) {
                        mp.stop();
                        mp.release();
                        mp=null;
                    }
                    else {
                        mp = null;
                    }
                    mp = MediaPlayer.create(context, R.raw.music);
                    mp.setLooping(false);       //no loop with music
                    mAudioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
                    int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);   // (flags: FLAG_SHOW_UI) show adjust UI on screen
                    mp.start();
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            if (!mp.isPlaying()) {
                                mp.release();
                            }
                            else {
                                mp.stop();
                                mp.release();
                            }
                        }
                    });
                    Intent i = new Intent(context, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
            }
        }
    }

    /*@RequiresApi(api = Build.VERSION_CODES.Q)
    public static String getTopActivity(Context context){
        ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        return cn.getClassName();
    }*/
}
