package com.itvillage.dev.torchlight_volumebutton;

/**
 * Created by monirozzamanroni on 6/25/2019.
 */


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootDeviceReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent intent2 = new Intent(context, MainActivity.class);
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent2);
        }
    }
}