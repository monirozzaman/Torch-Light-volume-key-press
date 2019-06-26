package com.itvillage.dev.torchlight_volumebutton;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Camera;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;


/**
 * Created by monirozzamanroni on 6/21/2019.
 */

public class backgroudRunningService extends Service {
    private static final int NOTIF_ID = 1;
    private static final String NOTIF_CHANNEL_ID = "Channel_Id";
    Camera camera;
    Camera.Parameters parameters;
    int count = 0;

    public backgroudRunningService() {

        super();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        createNotificationC();
        final BroadcastReceiver vReceiver = new BroadcastReceiver() {


            @Override
            public void onReceive(Context context, Intent intent) {

                PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
                boolean isScreenOn = powerManager.isScreenOn();
                if (!isScreenOn) {
                    //* -------------------- create Toggle Button-------------------------*//*
                    count++;
                    if (count == 2) {
                        camera = Camera.open();
                        parameters = camera.getParameters();
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        camera.setParameters(parameters);
                        camera.startPreview();
                    } else if (count == 4) {
                        camera = Camera.open();
                        parameters = camera.getParameters();
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        camera.setParameters(parameters);
                        camera.stopPreview();
                        camera.release();
                        count = 0;
                    } else {

                    }
                    //*--------------------- Screen Light On----------------*//*
//                    PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
//                    PowerManager.WakeLock mWakeLock = pm.newWakeLock((PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "YourServie");
//                    mWakeLock.acquire();
                    //  stopService(new Intent(backgroudRunningService.this, backgroudRunningService.class));
                } else {
                    count++;
                    if (count == 2) {
                        camera = Camera.open();
                        parameters = camera.getParameters();
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        camera.setParameters(parameters);
                        camera.startPreview();
                    } else if (count == 4) {
                        camera = Camera.open();
                        parameters = camera.getParameters();
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        camera.setParameters(parameters);
                        camera.stopPreview();
                        camera.release();
                        count = 0;
                    } else {

                    }

                }
            }

        };

        registerReceiver(vReceiver, new IntentFilter("android.media.VOLUME_CHANGED_ACTION"));
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground();

        return START_STICKY;
    }
    private void startForeground() {
        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        startForeground(NOTIF_ID, new NotificationCompat.Builder(this,
                NOTIF_CHANNEL_ID) // don't forget create a notification channel first
                .setOngoing(true)
                .setSmallIcon(R.drawable.loho2)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Enable Torch Light Mode")
                .setContentIntent(pendingIntent)
                .build());
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void createNotificationC() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIF_CHANNEL_ID,
                    "Channel_Id", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
    }
}


