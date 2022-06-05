package com.github.krottv.tmstemp.worker.grayscale

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.github.krottv.tmstemp.worker.upload.SongUploadWorker

class ImageGrayscaleWork(
    appContext: Context, params: WorkerParameters,
    private val imageGetterForWork: ImageGetterForWork,
    private val grayscaleApplier: GrayscaleApplier,
    private val imageSaver: ImageSaver
) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {

        val imagePath = inputData.getString(KEY_INPUT_PATH)!!
        val image = imageGetterForWork.getImage(imagePath)
        val processed = grayscaleApplier.apply(image)
        val path = imageSaver.saveImage(processed, SAVED_IMAGE_PATH)

        return Result.success(Data.Builder().putString(SongUploadWorker.KEY_IMAGE_PATH, path).build())
    }

    companion object {
        const val SAVED_IMAGE_PATH = "saved_image.png"
        const val KEY_INPUT_PATH = "input_path"
    }
}