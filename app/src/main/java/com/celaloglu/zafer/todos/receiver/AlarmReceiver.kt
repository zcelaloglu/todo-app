package com.celaloglu.zafer.todos.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.celaloglu.zafer.todos.R
import com.celaloglu.zafer.todos.ui.list.TodoListActivity
import com.celaloglu.zafer.todos.usecase.TodoUseCase
import com.celaloglu.zafer.todos.util.Constants
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import kotlin.coroutines.CoroutineContext

class AlarmReceiver : BroadcastReceiver(), CoroutineScope, KoinComponent {

    private val todoUseCase: TodoUseCase by inject()

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.extras?.let {
            val id = intent.extras?.get(Constants.INTENT_EXTRA_ID) as Int
            val title = intent.extras?.get(Constants.INTENT_EXTRA_TITLE) as String
            val description = intent.extras?.get(Constants.INTENT_EXTRA_DESCRIPTION) as String
            showNotification(context, title, description)
            setTodoItemCompleted(id)
        }
    }

    private fun showNotification(context: Context?, title: String, body: String) {
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(context, channelId)
                .apply {
                    setSmallIcon(R.mipmap.ic_launcher)
                    setContentTitle(title)
                    setContentText(body)
                    setAutoCancel(true)
                }

        val stackBuilder = TaskStackBuilder.create(context).apply {
            addNextIntent(Intent(context, TodoListActivity::class.java))
        }
        val resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        notificationBuilder.setContentIntent(resultPendingIntent)
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    private fun setTodoItemCompleted(id: Int) {
        launch(Dispatchers.IO) {
            todoUseCase.updateTodo(id)
        }
    }

    companion object {
        const val notificationId = 1
        const val channelId = "channelId"
        const val channelName = "Channel Name"
    }

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job
}