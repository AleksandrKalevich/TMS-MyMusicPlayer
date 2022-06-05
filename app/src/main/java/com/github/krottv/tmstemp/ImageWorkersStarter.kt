package com.github.krottv.tmstemp

import androidx.work.*
import com.github.krottv.tmstemp.worker.grayscale.ImageGrayscaleWork
import com.github.krottv.tmstemp.worker.upload.SongUploadWorker
import java.util.concurrent.TimeUnit

class ImageWorkersStarter(private val workManager: WorkManager) {

    fun start() {

        val grayscaleWork = OneTimeWorkRequestBuilder<ImageGrayscaleWork>()
            .addTag("image")
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
                    .putString(ImageGrayscaleWork.KEY_INPUT_PATH, "some_image")
                    .build()
            )
            .build()


        val uploadWork = OneTimeWorkRequestBuilder<SongUploadWorker>()
            .addTag("song")
            .build()

        workManager.beginUniqueWork(ALL_UNIQUE_WORK_NAME, ExistingWorkPolicy.REPLACE, grayscaleWork)
            .then(uploadWork)
            .enqueue()

    }

    companion object {
        const val ALL_UNIQUE_WORK_NAME = "unique_work_name"
    }
}