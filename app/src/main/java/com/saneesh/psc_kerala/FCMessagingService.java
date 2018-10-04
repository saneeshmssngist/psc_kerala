package com.saneesh.psc_kerala;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.saneesh.psc_kerala.Activities.QuestionPaperHomeActivity;

public class FCMessagingService extends FirebaseMessagingService {


    NotificationCompat.Builder notification;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        String title = remoteMessage.getData().get("title");
        String message = remoteMessage.getData().get("message");
        String image_url = remoteMessage.getData().get("largeIcon");

//       String image_url = remoteMessage.getNotification().getTitle();

        Intent intent = new Intent(this,QuestionPaperHomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        notification = new NotificationCompat.Builder(this);


//Building notifcation
        notification.setSmallIcon(R.mipmap.ic_launcher);
        Bitmap bm = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);
        notification.setLargeIcon(bm);
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle(title);
        notification.setContentText(message);
//        notification.setTicker("Notifiication from Quizrr");
        notification.setAutoCancel(true);



//        notification.setStyle(new NotificationCompat.BigTextStyle()
//                .bigText(message));

        notification.setLights(Color.BLUE, 500, 500);
        long[] pattern = {500,500,500,500,500,500,500,500,500};
        notification.setVibrate(pattern);

        notification.setContentIntent(pendingIntent);

//For fetching the image data from internet by using url
        ImageRequest imageRequest = new ImageRequest(image_url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {

                notification.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(response));
                //Issuing the notification
                NotificationManager nmr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                nmr.notify(0,notification.build());

            }
        }, 0, 0, null, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Singleton.getInstance(this).addToRequestque(imageRequest);


    }

}
