package com.example.administrator.googlenavigationtest;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String str_rideId;
    private Button btnStart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGetServiceClick = new GetFloatingIconClick();

        btnStart = findViewById(R.id.mainStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askDrawOverPermission();
            }
        });

        new KThread().start();
    }
    public static final String BROADCAST_ACTION = "com.example.administrator.googlenavigationtest.MainActivity";

    public class KThread extends Thread{
        @Override
        public void run(){
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            askDrawOverPermission();
        }
    }

    private GetFloatingIconClick mGetServiceClick;
    public static boolean isFloatingIconServiceAlive = false;

    private void askDrawOverPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // if OS is pre-marshmallow then create the floating icon, no permission is needed
            createFloatingBackButton();
        } else {
            if (!Settings.canDrawOverlays(this)) {
                // asking for DRAW_OVER permission in settings
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getApplicationContext().getPackageName()));
                startActivityForResult(intent, 8979);
            } else {
                createFloatingBackButton();
            }
        }
    }

    // starting service for creating a floating icon over map
    private void createFloatingBackButton() {
        Intent iconServiceIntent = new Intent(MainActivity.this, FloatingOverMapIconService.class);
        iconServiceIntent.putExtra("RIDE_ID", str_rideId);

        Intent navigation = new Intent(Intent.ACTION_VIEW, Uri
                .parse("google.navigation:q=" + 40.7548237 + "," + -73.8045325 + "&mode=d"));
        navigation.setPackage("com.google.android.apps.maps");
        startActivityForResult(navigation, 1234);

        startService(iconServiceIntent);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 8979) {
            // as permissions from Settings don't provide any callbacks, hence checking again for the permission
            // so that we can draw our floating without asking user to click on the previously clicked view
            // again
            if (Settings.canDrawOverlays(this)) {
                createFloatingBackButton();
            } else {
                //permission is not provided by user, do your task
                //GlobalVariables.alert(mContext, "This permission is necessary for this application's functioning");
            }
        } else if (requestCode == 1234) {
            // no result is returned by google map, as google don't provide any apis or documentation
            // for it.
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
