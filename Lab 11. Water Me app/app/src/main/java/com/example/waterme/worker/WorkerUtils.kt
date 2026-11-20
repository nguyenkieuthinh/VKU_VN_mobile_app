package com.example.waterme.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.waterme.CHANNEL_ID
import com.example.waterme.MainActivity
import com.example.waterme.NOTIFICATION_ID
import com.example.waterme.R
import com.example.waterme.REQUEST_CODE

/**
 * Creates the "Watering reminders" notification channel and posts the reminder
 * notification that opens the app when tapped.
 */
fun makePlantReminderNotification(
    message: String,
    context: Context
) {
    createNotificationChannel(context)

    val pendingIntent = createPendingIntent(context)

    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_water_drop)
        .setContentTitle(context.getString(R.string.notification_title))
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setCategory(NotificationCompat.CATEGORY_REMINDER)
        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        .setVibrate(LongArray(0))
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

    val notificationManager = NotificationManagerCompat.from(context)
    if (notificationManager.areNotificationsEnabled()) {
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }
}

private fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(
            CHANNEL_ID,
            context.getString(R.string.notification_channel_name),
            importance
        ).apply {
            description = context.getString(R.string.notification_channel_description)
        }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        notificationManager?.createNotificationChannel(channel)
    }
}

private fun createPendingIntent(appContext: Context): PendingIntent {
    val intent = Intent(appContext, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    // FLAG_IMMUTABLE is required for PendingIntents on Android 12 and higher.
    val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

    return PendingIntent.getActivity(
        appContext,
        REQUEST_CODE,
        intent,
        flags
    )
}
