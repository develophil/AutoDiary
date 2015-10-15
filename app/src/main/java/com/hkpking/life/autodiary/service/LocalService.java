package com.hkpking.life.autodiary.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.hkpking.life.autodiary.R;
import com.hkpking.life.autodiary.common.CommonDefines;

import java.util.List;
import java.util.Locale;

public class LocalService extends Service implements CommonDefines {
    private NotificationManager mNM;
    public int i = 0;

    // Unique Identification Number for the Notification.
    // We use it on Notification start, and to cancel it.
    private int NOTIFICATION = R.string.local_service_started;

    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder {
        LocalService getService() {
            return LocalService.this;
        }
    }

    @Override
    public void onCreate() {
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        // Display a notification about us starting.  We put an icon in the status bar.
        showNotification();
        Log.i(TAG, "service onCreate >>>>>>>>>>>>>>");
    }

    private void updateThread() {
        i++;
        Log.i(TAG, "i >>> "+i);




//and in your main class(Activity), call this class as follows :
        double latitude = 0.0, longitude = 0.0;
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        LocationTracker locationTracker = new LocationTracker(this);

        if(locationTracker.canGetLocation()) {

            latitude = locationTracker.getLatitude();
            longitude = locationTracker.getLongitude();
            Log.i(TAG, "LatLong: " + latitude + ", " + longitude);

            try{
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

                if (addresses.size() >  0) {

                    String address = addresses.get(0).getAddressLine(0);
                    String city = addresses.get(0).getAddressLine(1);
                    String country = addresses.get(0).getAddressLine(2);
                    String completeAddress = address + ", " + city + ", "+country;

                    Log.i(TAG, completeAddress);

                }
            }
            catch (Exception e){
                Log.e(TAG, e.getMessage());
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Log.i(TAG, "Unable to get location");
        }


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        Log.i(TAG, "onStartCommand >>>>>>>>>>>>>>");
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        Toast.makeText(this, "onStartCommand start!", Toast.LENGTH_SHORT).show();




        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                updateThread();
            }
        };

        Thread myThread = new Thread(new Runnable() {
            public void run() {
                while (i<2) {
                    try {
                        handler.sendMessage(handler.obtainMessage());
                        Thread.sleep(5000);
                    } catch (Throwable t) {
                    }
                }
            }
        });

        myThread.start();


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        // Cancel the persistent notification.
        mNM.cancel(NOTIFICATION);

        // Tell the user we stopped.
        Toast.makeText(this, "서비스가 백그라운드에서 작동합니다..", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private final IBinder mBinder = new LocalBinder();

    /**
     * Show a notification while this service is running.
     */
    private void showNotification() {
        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = getText(R.string.local_service_started);

        // Set the icon, scrolling text and timestamp
        Notification notification = new Notification(R.drawable.stat_sample, text,
                System.currentTimeMillis());

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, LocalServiceActivities.Controller.class), 0);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(this, getText(R.string.local_service_label),
                text, contentIntent);

        // Send the notification.
        mNM.notify(NOTIFICATION, notification);
    }
}