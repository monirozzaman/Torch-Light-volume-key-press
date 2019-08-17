package com.itvillage.dev.torchlight_volumebutton;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 50;
    Switch onoff;
    private BottomSheetDialog bottomSheetDialog;
    private InterstitialAd mInterstitialAd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onoff = (Switch) findViewById(R.id.switch1);

        MobileAds.initialize(this,
                getString(R.string.appId));

        // Interstitial Ads Two
        mInterstitialAd2 = new InterstitialAd(this);
        mInterstitialAd2.setAdUnitId(getString(R.string.interstitial_2));
        mInterstitialAd2.loadAd(new AdRequest.Builder().build());
        boolean isEnabled = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
        if (!isEnabled) {
            Toast.makeText(MainActivity.this, "Camera Permission Needed", Toast.LENGTH_SHORT).show();
        } else {
            onoff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (mInterstitialAd2.isLoaded()) {
                            ServiceOprn();
                            mInterstitialAd2.show();
                        } else {
                            ServiceOprn();
                            Log.d("TAG", "The interstitial wasn't loaded yet.");
                        }

                    } else {
                        stopService(new Intent(MainActivity.this, backgroudRunningService.class));
                        ImageView imageView = findViewById(R.id.a);
                        Switch aSwitch = findViewById(R.id.switch1);
                        imageView.setBackgroundColor(Color.parseColor("#fd5a65"));
                        aSwitch.setBackgroundColor(Color.parseColor("#68da5f6a"));
                        Toast.makeText(getApplicationContext(), "Mode Disable", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        // Call showManu()
        //showManu();
    }

    private void ServiceOprn() {
        // //Servives Open
        startService(new Intent(MainActivity.this, backgroudRunningService.class));
        //Notify
        ImageView imageView = findViewById(R.id.a);
        Switch aSwitch = findViewById(R.id.switch1);
        imageView.setBackgroundColor(Color.parseColor("#FFA2CF75"));
        aSwitch.setBackgroundColor(Color.parseColor("#FFA2CF75"));
        Toast.makeText(getApplicationContext(), "Mode Enable", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(MainActivity.this, "Permission Denied for the Camera", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /*
    * App Menu Bottom Sheet
    * */
    public void showManu() {
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet, null);

        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();

    }

}
