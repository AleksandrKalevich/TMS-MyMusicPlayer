package com.github.krottv.tmstemp.worker.upload

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.github.krottv.tmstemp.R
import com.github.krottv.tmstemp.presentation.SongDownloadViewModel

class SongUploadWorker(
    private val songDownloadViewModel: SongDownloadViewModel,
    appContext: Context,
    params: WorkerParameters
) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {

        setForeground(getForegroundInfo())

        val songPath = inputData.getString(KEY_SONG_PATH)!!
        val saveFilePath = inputData.getString(SAVE_FILE_PATH)!!

        return try {
            songDownloadViewModel.downloadSong(songPath, saveFilePath).collect {
                setProgress(Data.Builder().putFloat("progress", it).build())
            }

            Result.success()
        } catch (e: Exception) {

            if (runAttemptCount >= MAX_RETRIES)
                Result.failure()
            else
                Result.retry()
        }
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(NOTIFICATION_ID, createNotification())
    }

    private fun createNotification(): Notification {

        val cancelAction = WorkManager.getInstance(applicationContext)
            .createCancelPendingIntent(id)

        val channelId = "uploadImageWorker"
        val notification = NotificationCompat.Builder(
            applicationContext, channelId
        )
            .setContentTitle("Downloading Your Image")
            .setTicker("Downloading Your Image")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setOngoing(true)
            .addAction(android.R.drawable.ic_delete, "Cancel Download", cancelAction)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(notification, channelId)
        }

        return notification.build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(
        notificationBuilder: NotificationCompat.Builder,
        id: String
    ) {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as
                    NotificationManager
        notificationBuilder.setDefaults(Notification.DEFAULT_VIBRATE)
        val channel = NotificationChannel(
            id,
            "WorkManagerApp",
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.description = "WorkManagerApp Notifications"
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        const val KEY_SONG_PATH = "song_path"
        const val SAVE_FILE_PATH = "where to save song"
        private const val NOTIFICATION_ID = 9274
        private const val MAX_RETRIES = 2
    }
}