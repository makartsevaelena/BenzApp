package com.makartsevaelena.benzapp;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

public class BenzApp extends Application implements BootstrapNotifier {
    private static final String TAG = "BenzApp";
    private RegionBootstrap regionBootstrap;
    private MainActivity mainActivity = null;
    BackgroundPowerSaver backgroundPowerSaver;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "App started up");
        BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().clear();
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
//        beaconManager.setEnableScheduledScanJobs(false);
//        beaconManager.setBackgroundBetweenScanPeriod(0);
//        beaconManager.setBackgroundScanPeriod(1100);

        Log.d(TAG, "setting up background monitoring for beacons and power saving");
        // wake up the app when a beacon is seen
        Region region = new Region("backgroundRegion",
                null, null, null);
        regionBootstrap = new RegionBootstrap(this, region);
        backgroundPowerSaver = new BackgroundPowerSaver(this);

    }

    @Override
    public void didEnterRegion(Region arg0) {
        Log.d(TAG, "I just saw an beacon for the first time!");
        regionBootstrap.disable();
        Log.d(TAG, "Sending notification.");
        sendNotification();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }

    @Override
    public void didExitRegion(Region arg0) {
        Log.d(TAG, "I no longer see an beacon");
    }

    @Override
    public void didDetermineStateForRegion(int arg0, Region arg1) {
        Log.d(TAG, "I have just switched from seeing/not seeing beacons: " + arg0);
    }


    private void sendNotification() {
        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("Beacon Reference Notifications",
                    "Beacon Reference Notifications", NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            notificationManager.createNotificationChannel(channel);
            builder = new Notification.Builder(this, channel.getId());
        } else {
            builder = new Notification.Builder(this);
            builder.setPriority(Notification.PRIORITY_HIGH);
        }

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(new Intent(this, MainActivity.class));
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        builder.setSmallIcon(R.mipmap.ic_launcher_benzapp_round);
        builder.setContentTitle("I detect a IBeacon");
        builder.setContentText("Tap here to open app and make order");
        builder.setContentIntent(resultPendingIntent);
        notificationManager.notify(1, builder.build());
    }

}
