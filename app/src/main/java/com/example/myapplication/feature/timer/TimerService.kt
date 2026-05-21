package com.example.myapplication.feature.timer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.domain.repository.TimerRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TimerService : LifecycleService() {

    @Inject
    lateinit var timerRepository: TimerRepository

    private var isRunning = false
    private var targetEndTime: Long = 0

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        
        when (intent?.action) {
            "START" -> startTimer()
            "STOP" -> stopTimer()
        }
        
        return START_STICKY
    }

    private fun startTimer() {
        if (isRunning) return
        isRunning = true
        
        targetEndTime = System.currentTimeMillis() + (3600 * 1000L)

        startForeground(1, createNotification("Timer Active", "Focus session in progress"))

        lifecycleScope.launch {
            while (isRunning) {
                val now = System.currentTimeMillis()
                val remainingMillis = targetEndTime - now
                
                if (remainingMillis <= 0) {
                    timerRepository.updateTimerSeconds(0)
                    stopTimer()
                } else {
                    timerRepository.updateTimerSeconds((remainingMillis / 1000).toInt())
                }
                
                delay(1000)
            }
        }
    }

    private fun stopTimer() {
        isRunning = false
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun createNotification(title: String, content: String): Notification {
        val channelId = "timer_channel"
        val channel = NotificationChannel(
            channelId,
            "Timer Notifications",
            NotificationManager.IMPORTANCE_LOW
        )
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setOngoing(true)
            .build()
    }
}
