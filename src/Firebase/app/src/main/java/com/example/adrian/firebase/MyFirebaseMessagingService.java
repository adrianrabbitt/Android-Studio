package com.example.adrian.firebase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by Adrian on 13/04/2018.
 */
//Recieve notification from FCM
//reference tutorial https://www.youtube.com/watch?v=XijS62iP1Xo&t=679s
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    Map<String,String> payload;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

      if(remoteMessage.getData().size() > 0)
      {
          payload = remoteMessage.getData();
      }


        showNotification(payload);


    }
   //Show notification when recieved.
    private void showNotification(Map<String,String> payload) {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder b = new NotificationCompat.Builder(getApplicationContext());
        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_email_black_24dp)
                .setContentTitle(payload.get("title"))
                .setContentText(payload.get("author"))
                .setContentIntent(contentIntent);
        NotificationManager notificationManager = (NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,b.build());
    }
}
