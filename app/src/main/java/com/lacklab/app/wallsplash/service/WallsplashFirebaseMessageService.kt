package com.lacklab.app.wallsplash.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.lacklab.app.wallsplash.MainActivity
import com.lacklab.app.wallsplash.R
import timber.log.Timber

class WallsplashFirebaseMessageService : FirebaseMessagingService() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        Timber.d("From: ${p0.from}")
        // For the stack overflow
        // https://stackoverflow.com/questions/37711082/
        // 1. Display Messages: These messages trigger the onMessageReceived()
        // callback only when your app is in foreground
        // 2. Data Messages: Theses messages trigger the onMessageReceived()
        // callback even if your app is in foreground/background/killed
        // HOW TO ?
        // Use the Api //fcm.googleapis.com/v1/projects/myproject-b5ae1/messages:send
        // instead of  https://fcm.googleapis.com/fcm/send
        // Check if message contains a data payload.
        if (p0.data.isNotEmpty()) {
            Timber.d("Message data payload: ${p0.data}")
        }

        // Check if message contains a notification payload.
        p0.notification?.let {
            Timber.d("Message Notification Body: ${it.body}")
            sendNotification(it)
        }
    }
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Timber.d("Refreshed token: $p0")
    }

    private fun sendNotification(notification: RemoteMessage.Notification) {
        // Set the notification's tap action
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT)

        val channelId = getString(R.string.high_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        // Setup Notification
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setContentTitle(getString(R.string.fcm_message))
            .setContentTitle(notification.title)
            .setContentText(notification.body)
            .setAutoCancel(true) // automatically removes the notification when the user taps it.
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent) //Set the notification's tap action
            .setDefaults(Notification.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH) // head-up

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_HIGH) // for heads-up notifications
            channel.description = "description"
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }
}