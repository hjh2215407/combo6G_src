package com.dabinsystems.pact_app.Recorder;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.R;

public class VideoRecService extends Service {
    public VideoRecService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        initializeNotification();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();
    }

    public void initializeNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1");
//        builder.setSmallIcon(R.drawable.app_icon);
//        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
//        style.bigText("Combo");
//        style.setBigContentTitle(null);
//        style.setSummaryText("서비스 동작중");
        //builder.setContentTitle("Combo Analyzer");
        builder.setContentText("Analysis service");
        builder.setOngoing(true);
        //builder.setStyle(style);
        builder.setWhen(0);
        builder.setShowWhen(false);

//        Intent intent = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0, new Intent[]{intent}, PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(new NotificationChannel("1", "Service", NotificationManager.IMPORTANCE_NONE));
        }

        Notification notification = builder.build();
        startForeground(1, notification);
    }

}