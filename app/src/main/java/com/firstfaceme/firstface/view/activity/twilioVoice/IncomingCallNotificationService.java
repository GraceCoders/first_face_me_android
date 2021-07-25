package com.firstfaceme.firstface.view.activity.twilioVoice;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.firstfaceme.firstface.R;


public class IncomingCallNotificationService extends Service {

    private static final String TAG = IncomingCallNotificationService.class.getSimpleName();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();

        if (action != null) {
            String callInvite = intent.getStringExtra(Constantss.INCOMING_CALL_INVITE);
            int notificationId = intent.getIntExtra(Constantss.INCOMING_CALL_NOTIFICATION_ID, 0);
            switch (action) {
                case Constantss.ACTION_INCOMING_CALL:
                    handleIncomingCall(callInvite, notificationId);
                    break;
                case Constantss.ACTION_ACCEPT:
                    accept(callInvite, notificationId);
                    break;
                case Constantss.ACTION_REJECT:
                    reject(callInvite);
                    break;
                case Constantss.ACTION_CANCEL_CALL:
                    handleCancelledCall(intent);
                    break;
                default:
                    break;
            }
        }
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Notification createNotification(String callInvite, int notificationId, int channelImportance) {
        
        
        Intent intent = new Intent(this, VoiceActivity.class);
        intent.setAction(Constantss.ACTION_INCOMING_CALL_NOTIFICATION);
        intent.putExtra(Constantss.INCOMING_CALL_NOTIFICATION_ID, notificationId);
        intent.putExtra(Constantss.INCOMING_CALL_INVITE, callInvite);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        /*
         * Pass the notification id and call sid to use as an identifier to cancel the
         * notification later
         */
        Bundle extras = new Bundle();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            return buildNotification(callInvite + " is calling.",
                    pendingIntent,
                    extras,
                    callInvite,
                    notificationId,
                    createChannel(channelImportance));
        } else {
            //noinspection deprecation
            return new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(callInvite+ " is calling.")
                    .setAutoCancel(true)
                    .setExtras(extras)
                    .setContentIntent(pendingIntent)
                    .setGroup("test_app_notification")
                    .setColor(Color.rgb(214, 10, 37)).build();
        }
    }

    /**
     * Build a notification.
     *
     * @param text          the text of the notification
     * @param pendingIntent the body, pending intent for the notification
     * @param extras        extras passed with the notification
     * @return the builder
     */
    @TargetApi(Build.VERSION_CODES.O)
    private Notification buildNotification(String text, PendingIntent pendingIntent, Bundle extras,
                                           final String callInvite,
                                           int notificationId,
                                           String channelId) {
        Intent rejectIntent = new Intent(getApplicationContext(), IncomingCallNotificationService.class);
        rejectIntent.setAction(Constantss.ACTION_REJECT);
        rejectIntent.putExtra(Constantss.INCOMING_CALL_INVITE, callInvite);
        rejectIntent.putExtra(Constantss.INCOMING_CALL_NOTIFICATION_ID, notificationId);
        PendingIntent piRejectIntent = PendingIntent.getService(getApplicationContext(), 0, rejectIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent acceptIntent = new Intent(getApplicationContext(), IncomingCallNotificationService.class);
        acceptIntent.setAction(Constantss.ACTION_ACCEPT);
        acceptIntent.putExtra(Constantss.INCOMING_CALL_INVITE, callInvite);
        acceptIntent.putExtra(Constantss.INCOMING_CALL_NOTIFICATION_ID, notificationId);
        PendingIntent piAcceptIntent = PendingIntent.getService(getApplicationContext(), 0, acceptIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder =
                new Notification.Builder(getApplicationContext(), channelId)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(text)
                        .setCategory(Notification.CATEGORY_CALL)
                        .setExtras(extras)
                        .setAutoCancel(true)
                        .addAction(android.R.drawable.ic_menu_delete, getString(R.string.decline), piRejectIntent)
                        .addAction(android.R.drawable.ic_menu_call, getString(R.string.answere), piAcceptIntent)
                        .setFullScreenIntent(pendingIntent, true);

        return builder.build();
    }

    @TargetApi(Build.VERSION_CODES.O)
    private String createChannel(int channelImportance) {
        NotificationChannel callInviteChannel = new NotificationChannel(Constantss.VOICE_CHANNEL_HIGH_IMPORTANCE,
                "Primary Voice Channel", NotificationManager.IMPORTANCE_HIGH);
        String channelId = Constantss.VOICE_CHANNEL_HIGH_IMPORTANCE;

        if (channelImportance == NotificationManager.IMPORTANCE_LOW) {
            callInviteChannel = new NotificationChannel(Constantss.VOICE_CHANNEL_LOW_IMPORTANCE,
                    "Primary Voice Channel", NotificationManager.IMPORTANCE_LOW);
            channelId = Constantss.VOICE_CHANNEL_LOW_IMPORTANCE;
        }
        callInviteChannel.setLightColor(Color.GREEN);
        callInviteChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(callInviteChannel);

        return channelId;
    }

    private void accept(String callInvite, int notificationId) {
        endForeground();
        Intent activeCallIntent = new Intent(this, VoiceActivity.class);
        activeCallIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activeCallIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activeCallIntent.putExtra(Constantss.INCOMING_CALL_INVITE, callInvite);
        activeCallIntent.putExtra(Constantss.INCOMING_CALL_NOTIFICATION_ID, notificationId);
        activeCallIntent.setAction(Constantss.ACTION_ACCEPT);
        startActivity(activeCallIntent);
    }

    private void reject(String callInvite) {
        endForeground();
    }

    private void handleCancelledCall(Intent intent) {
        endForeground();
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void handleIncomingCall(String callInvite, int notificationId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setCallInProgressNotification(callInvite, notificationId);
        }
        sendStringToActivity(callInvite, notificationId);
    }

    private void endForeground() {
        stopForeground(true);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void setCallInProgressNotification(String callInvite, int notificationId) {
        if (isAppVisible()) {
            Log.i(TAG, "setCallInProgressNotification - app is visible.");
            startForeground(notificationId, createNotification(callInvite, notificationId, NotificationManager.IMPORTANCE_LOW));
        } else {
            Log.i(TAG, "setCallInProgressNotification - app is NOT visible.");
            startForeground(notificationId, createNotification(callInvite, notificationId, NotificationManager.IMPORTANCE_HIGH));
        }
    }

    /*
     * Send the String to the VoiceActivity. Start the activity if it is not running already.
     */
    private void sendStringToActivity(String callInvite, int notificationId) {
        if (Build.VERSION.SDK_INT >= 29 && !isAppVisible()) {
            return;
        }
        Intent intent = new Intent(this, VoiceActivity.class);
        intent.setAction(Constantss.ACTION_INCOMING_CALL);
        intent.putExtra(Constantss.INCOMING_CALL_NOTIFICATION_ID, notificationId);
        intent.putExtra(Constantss.INCOMING_CALL_INVITE, callInvite);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }

    private boolean isAppVisible() {
        return ProcessLifecycleOwner
                .get()
                .getLifecycle()
                .getCurrentState()
                .isAtLeast(Lifecycle.State.STARTED);
    }
}
