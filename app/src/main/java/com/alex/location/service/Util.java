package com.alex.location.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.alex.location.R;

/**
 * Global utility methods
 */

public class Util {
    public static final int FOREGROUND_NOTIFICATION_ID = 51288;

    private static Util util = null;

    private static final String NOTIFICATIONS_CHANNEL_ID_MAIN_NOTIFICATIONS = "1001";
    private static final String NOTIFICATIONS_CHANNEL_NAME_MAIN_NOTIFICATIONS = "Main notifications";


    /**
     * Create notification for status bar
     *
     * @param context Context
     * @return Notification
     */
    public static Notification createStatusBarNotification(Context context, String text, Bitmap weather) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATIONS_CHANNEL_ID_MAIN_NOTIFICATIONS)
//                .setContentTitle(context.getResources().getString(R.string.notification_title))
//                .setContentText(context.getResources().getString(R.string.notification_text))
//                .setContentTitle(text).setContentText("Current temperature\n" + "\n" + wendu).setSmallIcon(R.mipmap.logo).setAutoCancel(false).setLargeIcon(weather);
                .setContentTitle(text).setContentText("service running").setSmallIcon(R.mipmap.logo).setAutoCancel(false).setLargeIcon(weather);
//        notificationBuilder.setContentIntent(DrawerActivity.Companion.startPendingIntent(context, ""));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && notificationManager != null) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATIONS_CHANNEL_ID_MAIN_NOTIFICATIONS, NOTIFICATIONS_CHANNEL_NAME_MAIN_NOTIFICATIONS, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(false);
            channel.setVibrationPattern(null);
            channel.setSound(null, null);
            notificationManager.createNotificationChannel(channel);
        }

        return notificationBuilder.build();
    }

}
