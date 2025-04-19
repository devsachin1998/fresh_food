package com.freshfoodz;

import static com.razorpay.AppSignatureHelper.TAG;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;


import androidx.core.app.NotificationCompat;

import com.freshfoodz.helper.PrefUtils;
import com.freshfoodz.ui.DashboardActivity;
import com.freshfoodz.ui.NotificationActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


/**
 * Created by Azizoglu on 30.12.2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public  void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getData());
String s =remoteMessage.getData().get("body");
String s1 =remoteMessage.getData().get("title");

        PrefUtils.saveStringValue(this, "IS_NOTIFICATION", "1");

        String count = PrefUtils.getStringValue(this,"NOTIFICATION_COUNT");
            if (count.equals("0") || count.equals(""))
                            {
                                count="0";
                                int n = Integer.parseInt(count) + 1;
                                String s2 = Integer.toString(n);
                                PrefUtils.saveStringValue(this, "NOTIFICATION_COUNT", s2);
                            }


//        Log.d(TAG, "onMessageReceived: " + remoteMessage.getData().get("message"));
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("IsNotification", "1");

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        String channelId = "Default";



        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher_foreground)
                .setContentTitle(s)
                .setContentText(s1).setAutoCancel(true).setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());

    }

    private void showNotification(RemoteMessage remoteMessage) {



    }

//        Intent i = new Intent(this, NotificationActivity.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
//                .setAutoCancel(true)
//                .setContentTitle("FCM Example")
//                .setContentText(message)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentIntent(pendingIntent);
//        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        manager.notify(0,builder.build());

}