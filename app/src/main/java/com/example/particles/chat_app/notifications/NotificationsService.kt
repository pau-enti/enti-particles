package com.example.particles.chat_app.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LifecycleService
import com.example.particles.R
import com.example.particles.chat_app.chat.ChatActivity
import com.example.particles.chat_app.chat.model.Chat


class NotificationsService : LifecycleService() {

    val notisVM = NotificationsViewModel()

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

        notisVM.subscribeCurrentContacts(this).forEach { observableChats ->
            observableChats.observe(this) {
                notifyNewMessage(it)
            }
        }
    }

    fun notifyNewMessage(chat: Chat) {
        if (chat.messages.isNullOrEmpty())
            return

        val intent = Intent(this, ChatActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            extras?.putString(ChatActivity.EXTRA_USER_ID, chat.getInterlocutor())
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)


        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_blur_on_24)
            .setContentTitle(chat.getInterlocutor())
            .setContentText(chat.messages.last().content)
            /*.setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Much longer text that cannot fit one line...")
            )*/
            .setPriority(NotificationCompat.PRIORITY_HIGH)

            // Fem que quan es cliqui, s'obri el xat i desaparegui la noti (amb l'autoCancel)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        // Mostrem la notificació
        with(NotificationManagerCompat.from(this)) {
            // El notificationId el podem recordar en cas que vulguem actualitzar la noti
            val notificationId = System.currentTimeMillis().toInt()
            notify(notificationId, builder.build())
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        // It's safe to call this repeatedly because creating an existing notification channel
        // performs no operation.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Chats"
            val descriptionText = "This channel will provide user chat notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            // Register the channel with the system
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val CHANNEL_ID = "CHATS_APP_NOTIFICATIONS_CHANNEL"
    }
}