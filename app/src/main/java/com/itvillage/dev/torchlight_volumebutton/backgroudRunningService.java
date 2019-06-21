package com.itvillage.dev.torchlight_volumebutton;


import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * Created by monirozzamanroni on 6/21/2019.
 */

public class backgroudRunningService extends Service {
//    KeyEvent event;
    private MediaPlayer player;
    private static final int NOTIF_ID = 1;
    private static final String NOTIF_CHANNEL_ID = "Channel_Id";
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId){
//
//        // do your jobs here
//
//        startForeground();
//
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    private void startForeground() {
//       player = MediaPlayer.create(getApplicationContext(), Settings.System.DEFAULT_RINGTONE_URI);
//       player.setLooping(true);
//      player.start();
//
//
//        //
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
//                notificationIntent, 0);
//
//        startForeground(NOTIF_ID, new NotificationCompat.Builder(this,
//                NOTIF_CHANNEL_ID) // don't forget create a notification channel first
//                .setOngoing(true)
//                .setSmallIcon(R.drawable.ic_launcher_background)
//                .setContentTitle(getString(R.string.app_name))
//                .setContentText("Service is running background")
//                .setContentIntent(pendingIntent)
//                .build());
//    }
//        @Override
//    public void onDestroy() {
//            player.stop();
//        super.onDestroy();
//    }
//
//}
//
//


    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
        final BroadcastReceiver vReceiver = new BroadcastReceiver() {


            @Override
            public void onReceive(Context context, Intent intent) {

                PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
                boolean isScreenOn = powerManager.isScreenOn();
                if (!isScreenOn)
                {

                    PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
                    PowerManager.WakeLock mWakeLock = pm.newWakeLock((PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "YourServie");
                    mWakeLock.acquire();
                    // The screen has been locked
                    // do stuff...
                    Toast.makeText(backgroudRunningService.this, "Screen Is On now "+isScreenOn, Toast.LENGTH_LONG).show();
                }
                else
                {
                    player = MediaPlayer.create(getApplicationContext(), Settings.System.DEFAULT_RINGTONE_URI);
                    player.setLooping(true);
                    player.setVolume(0f,0f);
                    player.start();

                  Toast.makeText(backgroudRunningService.this, "Screen Is On"+isScreenOn, Toast.LENGTH_LONG).show();
                }

            }

        };
        registerReceiver(vReceiver, new IntentFilter("android.media.VOLUME_CHANGED_ACTION"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        // do your jobs here

        startForeground();

        return super.onStartCommand(intent, flags, startId);
    }

    private void startForeground() {
        Toast.makeText(backgroudRunningService.this, "start", Toast.LENGTH_LONG).show();
//       player = MediaPlayer.create(getApplicationContext(), Settings.System.DEFAULT_RINGTONE_URI);
//       player.setLooping(true);
//      player.start();


        //
        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        startForeground(NOTIF_ID, new NotificationCompat.Builder(this,
                NOTIF_CHANNEL_ID) // don't forget create a notification channel first
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Service is running background")
                .setContentIntent(pendingIntent)
                .build());
    }
        @Override
    public void onDestroy() {
            Toast.makeText(backgroudRunningService.this, "off", Toast.LENGTH_LONG).show();
          //  player.stop();
        super.onDestroy();
    }
}

