package com.itvillage.dev.torchlight_volumebutton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Switch  onoff;
    KeyEvent event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onoff = (Switch) findViewById(R.id.switch1);
        onoff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                   startService(new Intent(MainActivity.this, backgroudRunningService.class));
                } else {
                    stopService(new Intent(MainActivity.this, backgroudRunningService.class));
                }
            }
        });

    }

//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        int action = event.getAction();
//        int keyCode = event.getKeyCode();
//        switch (keyCode) {
//            case KeyEvent.KEYCODE_VOLUME_UP:
//                if (action == KeyEvent.ACTION_DOWN) {
//                    Toast.makeText(getApplicationContext(), "up", Toast.LENGTH_LONG).show();
//                }
//                return true;
//            case KeyEvent.KEYCODE_VOLUME_DOWN:
//                if (action == KeyEvent.ACTION_DOWN) {
//                    Toast.makeText(getApplicationContext(), "down", Toast.LENGTH_LONG).show();
//                }
//                return true;
//            default:
//                return super.dispatchKeyEvent(event);
//        }
//    }
}
