package com.example.particles.ui.chat.notifications

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
import com.example.particles.ui.chat.User
import com.example.particles.ui.chat.chat.ChatActivity
import com.example.particles.ui.chat.chat.model.Chat


class NotificationsService : LifecycleService() {

    val notisVM = NotificationsViewModel()

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

        notisVM.subscribeCurrentContacts(this).forEach { observableChats ->
            observableChats.observe(this) {
                if (it.messages.lastOrNull()?.author != User.current)
                    notifyNewMessage(it)
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    fun notifyNewMessage(chat: Chat) {
        if (chat.messages.isEmpty())
            return

        val intent = Intent(this, ChatActivity::class.java).apply {
            // Intent.FLAG_ACTIVITY_NEW_TASK =  indica que la actividad debe iniciarse como la raíz
            // de una nueva tarea. Esto significa que la actividad se colocará en la raíz de la pila
            // de tareas y será el punto de partida para una nueva tarea.


            // Intent.FLAG_ACTIVITY_CLEAR_TASK = indica que todas las actividades en la tarea
            // deben borrarse antes de que se inicie la nueva actividad. Esto significa que cuando
            // se inicie la nueva actividad, será la única actividad en la tarea y el usuario no
            // podrá volver a las actividades anteriores en la tarea utilizando la tecla BACK.

            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(ChatActivity.EXTRA_USER_ID, chat.whoIsInterlocutor())
        }

        //  Un PendingIntent es un objeto que se puede utilizar para enviar una intención (un
        //  mensaje que indica a Android que debe realizar una acción) en el futuro.

        // PendingIntent.FLAG_UPDATE_CURRENT si l'activity existeix, la manté però la refresca
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_blur_on_24)
            .setContentTitle(chat.whoIsInterlocutor())
            .setContentText(chat.messages.last().content)
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