package com.github.krottv.tmstemp.worker

import androidx.work.*
import com.github.krottv.tmstemp.worker.upload.SongUploadWorker
import java.util.concurrent.TimeUnit

class SongUploaderStarter(private val workManager: WorkManager) {

    fun start(url: String, saveFilePath: String) {

        val songUploadWork = OneTimeWorkRequestBuilder<SongUploadWorker>()
            .addTag("songDownload")
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .keepResultsForAtLeast(10L, TimeUnit.MINUTES)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 1, TimeUnit.MINUTES)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .setInputData(
                Data.Builder()
                    .putString(SongUploadWorker.KEY_SONG_PATH, url)
                    .putString(SongUploadWorker.SAVE_FILE_PATH, saveFilePath)
                    .build()
            )
            .build()


        val uploadWork = OneTimeWorkRequestBuilder<SongUploadWorker>()
            .addTag("songDownload")
            .setInputMerger(OverwritingInputMerger::class.java)
            .build()

        workManager.beginUniqueWork(ALL_UNIQUE_WORK_NAME, ExistingWorkPolicy.REPLACE, songUploadWork)
            .then(uploadWork)
            .enqueue()

    }

    companion object {
        const val ALL_UNIQUE_WORK_NAME = "unique_work_name"
    }
}