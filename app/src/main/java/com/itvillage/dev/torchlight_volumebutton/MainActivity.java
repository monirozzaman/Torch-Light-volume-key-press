package com.itvillage.dev.torchlight_volumebutton;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 50;
    Switch onoff;
    private Dialog dialog;
    private InterstitialAd mInterstitialAd, mInterstitialAd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog);
        onoff = (Switch) dialog.findViewById(R.id.switch1);

        MobileAds.initialize(this,
                "ca-app-pub-5203976193543346~5455133438");
// Interstitial Ads One
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5203976193543346/1763300435");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
// Interstitial Ads Two
        mInterstitialAd2 = new InterstitialAd(this);
        mInterstitialAd2.setAdUnitId("ca-app-pub-5203976193543346/6316611356");
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
                        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
                        text.setText("Disable Torch Mode ?");
                        startService(new Intent(MainActivity.this, backgroudRunningService.class));
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        } else {
                            Log.d("TAG", "The interstitial wasn't loaded yet.");
                        }
                        //Custom Toast
                        LayoutInflater li = getLayoutInflater();
                        //Getting the View object as defined in the customtoast.xml file
                        View layout = li.inflate(R.layout.customtoast, (ViewGroup) findViewById(R.id.custom_toast_layout));
                        LinearLayout linearLayout = layout.findViewById(R.id.custom_toast_layout);
                        TextView textView = layout.findViewById(R.id.custom_toast_message);
                        linearLayout.setBackgroundColor(Color.parseColor("#0da62c"));
                        textView.setText("Enable Torch Light Mode.. \n Press Volume Key");
                        //Creating the Toast object
                        Toast toast = new Toast(getApplicationContext());
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);//setting the view of custom toast layout
                        toast.show();
                        dialog.show();
                    } else {
                        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
                        text.setText("Enable Torch Mode ?");
                        stopService(new Intent(MainActivity.this, backgroudRunningService.class));
                        if (mInterstitialAd2.isLoaded()) {
                            mInterstitialAd2.show();
                        } else {
                            Log.d("TAG", "The interstitial wasn't loaded yet.");
                        }
                        //Custom Toast
                        LayoutInflater li = getLayoutInflater();
                        //Getting the View object as defined in the customtoast.xml file
                        View layout = li.inflate(R.layout.customtoast, (ViewGroup) findViewById(R.id.custom_toast_layout));
                        LinearLayout linearLayout = layout.findViewById(R.id.custom_toast_layout);
                        TextView textView = layout.findViewById(R.id.custom_toast_message);
                        linearLayout.setBackgroundColor(Color.parseColor("#e12a36"));
                        textView.setText("Disable Torch Light Mode");
                        //Creating the Toast object
                        Toast toast = new Toast(getApplicationContext());
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);//setting the view of custom toast layout
                        toast.show();
                        dialog.show();
                    }
                }
            });
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
                    text.setText("Disable Torch Mode ?");
                    onoff.setChecked(true);
                    startService(new Intent(MainActivity.this, backgroudRunningService.class));
                    //Custom Toast
                    LayoutInflater li = getLayoutInflater();
                    //Getting the View object as defined in the customtoast.xml file
                    View layout = li.inflate(R.layout.customtoast, (ViewGroup) findViewById(R.id.custom_toast_layout));
                    LinearLayout linearLayout = layout.findViewById(R.id.custom_toast_layout);
                    TextView textView = layout.findViewById(R.id.custom_toast_message);
                    linearLayout.setBackgroundColor(Color.parseColor("#0da62c"));
                    textView.setText("Enable Torch Light Mode.. \n Press Volume Key");
                    //Creating the Toast object
                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);//setting the view of custom toast layout
                    toast.show();
                    dialog.show();
                } else {
                    Toast.makeText(MainActivity.this, "Permission Denied for the Camera", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
