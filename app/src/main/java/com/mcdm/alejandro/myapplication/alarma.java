package com.mcdm.alejandro.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.mcdm.alejandro.myapplication.SQLite.SQLCobrale;

/**
 * Created by AlejandroMissael on 19/04/2017.
 */

public class alarma extends BroadcastReceiver {
    private static final String TAG = "alarma";
    private SQLCobrale db;

    @Override
    public void onReceive(Context context, Intent intent) {
        db = new SQLCobrale(context);
        crearNotificacion(context);
    }

    private void crearNotificacion(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_cash)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentTitle("Hay clientes por cobrar")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentText("Tienes "+db.getDebenHoyCantidad() + " clientes por cobrar el día de hoy");

       /*Intent openHomePageActivity = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(openHomePageActivity);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(resultPendingIntent);*/
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationBuilder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;

        manager.notify(0, notificationBuilder.build());

    }
}
