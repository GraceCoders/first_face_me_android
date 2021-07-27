package com.sea.seaconnect.fcm

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.firstfaceme.firstface.R
import com.firstfaceme.firstface.view.activity.ChatActivity
import com.firstfaceme.firstface.view.activity.twilioVoice.Constantss
import com.firstfaceme.firstface.view.activity.twilioVoice.IncomingCallNotificationService
import com.firstfaceme.firstface.view.activity.twilioVoice.VoiceActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


/**
 Created by Gurpreet on 16/08/20.
 */

class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {

        // Push notification types
        const val PUSH_TYPE_CONNECTION = "ConnectionRequestSent"
        const val PUSH_TYPE_CHAT = "chat"


        private const val PARAM_KEY_PUSH_TYPE = "type"
        private const val PARAM_KEY_MESSAGE_TO_DISPLAY = "name"
        private const val PARAM_KEY_QUEUEID = "queId"
        private const val PARAM_KEY_NAME = "Name"
        private const val PARAM_KEY_PROFILE_IMAGENAME = "profileImage"


        // Notification channel data
        private const val PACKAGE_NAME = "com.firstfaceme.firstface"

        private const val CHANNEL_NAME_NEW_OFFER_NOTIFICATIONS = "NEW CONNECTION"

        private const val CHANNEL_ID_CONTRCT_REMINDER_NOTIFICATIONS =
            "$PACKAGE_NAME.contractReminderNotifications"
        private const val CHANNEL_NAME_CONTRCT_REMINDER_NOTIFICATIONS = "Contract Reminder"

        private const val CHANNEL_ID_ADMIN_NOTIFICATIONS = "$PACKAGE_NAME.adminNotifications"
        private const val CHANNEL_NAME_ADMIN_NOTIFICATIONS = "Admin"

    }


    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
//        sendRegistrationToServer(token)
    }

    private var mNotificationManager: NotificationManager? = null

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        val mapData = remoteMessage.data

        Log.e("TAG", "onMessageReceived: " + mapData.toString())

        if (remoteMessage.notification != null) {
            Log.e("TAG", "Notification Body: " + remoteMessage.notification!!.body)
            //                handleNotification(remoteMessage.getNotification().getBody());
          val   msg = remoteMessage.notification!!.body
            Log.e("TAG", "onMessageReceived: " + msg.toString())
            Log.e("TAG", "onMessageReceived: " + msg.toString())

        }
        handleInvite(mapData[PARAM_KEY_NAME]!!,mapData[PARAM_KEY_QUEUEID],mapData[PARAM_KEY_PROFILE_IMAGENAME])
        if (null == mNotificationManager) {
            mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager
        }
      /*  sendGeneralNotification(
            mapData[PARAM_KEY_MESSAGE_TO_DISPLAY] ?: "",
            CHANNEL_ID_ADMIN_NOTIFICATIONS,
            CHANNEL_NAME_ADMIN_NOTIFICATIONS
        )*/
        if (mapData.containsKey(PARAM_KEY_PUSH_TYPE)

        ) {

            when (mapData[PARAM_KEY_PUSH_TYPE]) {


                PUSH_TYPE_CONNECTION -> {
                    sendConnectiontNotifications(
                        mapData[PARAM_KEY_MESSAGE_TO_DISPLAY] ?: "",
                        CHANNEL_ID_CONTRCT_REMINDER_NOTIFICATIONS,
                        CHANNEL_NAME_CONTRCT_REMINDER_NOTIFICATIONS
                    )
                }

                else -> {
                    sendGeneralNotification(
                        mapData[PARAM_KEY_MESSAGE_TO_DISPLAY] ?: "",
                        CHANNEL_ID_ADMIN_NOTIFICATIONS,
                        CHANNEL_NAME_ADMIN_NOTIFICATIONS
                    )
                }
            }
        } else {
            sendGeneralNotification(
                mapData[PARAM_KEY_MESSAGE_TO_DISPLAY] ?: "",
                CHANNEL_ID_ADMIN_NOTIFICATIONS,
                CHANNEL_NAME_ADMIN_NOTIFICATIONS
            )
        }

    }
    private fun handleInvite(name: String, queID: String?, image: String?) {
        val intent =
            Intent(this, IncomingCallNotificationService::class.java)
        intent.action = Constantss.ACTION_INCOMING_CALL
        intent.putExtra(Constantss.INCOMING_QUE_ID, queID)
        intent.putExtra(Constantss.INCOMING_QUE_NAME, name)
        intent.putExtra(Constantss.INCOMING_QUE_IMAGE, image)
        startService(intent)
    }


    private fun sendConnectiontNotifications(
        messageToDisplay: String,
        channelId: String, channelName: String
    ) {
        Log.e("TAG", "sendChatNotification: " + "connection")


        val intent = Intent(this@MyFirebaseMessagingService, VoiceActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        mNotificationManager?.notify(
            2, getNotification(
                contentMessage = messageToDisplay,
                pendingIntent = PendingIntent
                    .getActivity(
                        this@MyFirebaseMessagingService, 2,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT
                    ),
                channelId = channelId,
                channelName = channelName
            )
        )




    }



    private fun sendGeneralNotification(
        messageToDisplay: String,
        channelId: String, channelName: String
    ) {
        Log.e("TAG", "sendChatNotification: " + "all")

        val intent = Intent(this, VoiceActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        mNotificationManager?.notify(
            0, getNotification(
                contentMessage = messageToDisplay,
                pendingIntent = PendingIntent.getActivity(
                    this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                ),
                channelId = channelId,
                channelName = channelName
            )
        )
    }


    @SuppressLint("NewApi")
    private fun getNotification(
        contentTitle: String = getString(R.string.app_name),
        contentMessage: String, pendingIntent: PendingIntent,
        channelId: String, channelName: String
    ): Notification {

        val sound = Uri.parse(
            ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                    packageName + "/" + R.raw.sound
        )

        if (null == mNotificationManager?.getNotificationChannel(channelId)) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName, NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.setShowBadge(true)
            notificationChannel.setSound(
                sound, AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION).build()
            )
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            mNotificationManager?.createNotificationChannel(notificationChannel)
        }
        return NotificationCompat.Builder(
            this, channelId
        )
            .setContentTitle(contentTitle)
            .setContentText(contentMessage)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(contentMessage)
            )
            .setSmallIcon(getNotificationIcon())
            .setTicker(contentTitle)
            .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
            .setSound(sound)
            .setDefaults(Notification.DEFAULT_ALL) // must requires VIBRATE permission
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setWhen(System.currentTimeMillis())
            .setContentIntent(pendingIntent)
            .setAutoCancel(true).build()
    }

    private val isOreoDevice: Boolean
        get() = android.os.Build.VERSION_CODES.O <= android.os.Build.VERSION.SDK_INT

    private fun getNotificationIcon(): Int {
        return if (isAboveLollipopDevice)
            R.drawable.logo
        else
            R.drawable.logo
    }
    /**
     * Create and show a simple notification containing the FCM message.
     */
    private fun showNotification(title: String?, body: String?, roomName: String) {
        val intent = Intent(this, ChatActivity::class.java)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.video)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = System.currentTimeMillis().toInt()
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    val isAboveLollipopDevice: Boolean
        get() = Build.VERSION_CODES.LOLLIPOP <= Build.VERSION.SDK_INT
}