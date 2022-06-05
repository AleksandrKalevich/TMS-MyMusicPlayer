package com.github.krottv.tmstemp.worker.grayscale

import android.content.Context
import android.graphics.Bitmap
import java.io.File

class ImageSaverLocalStorage(val context: Context) : ImageSaver {

    override fun saveImage(bitmap: Bitmap, newName: String): String {
        val newFile = getFileToSave(context, newName)
        val outputStream = newFile.outputStream()

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)

        return newFile.absolutePath
    }

    companion object {
        fun getFileToSave(context: Context, newName: String): File {
            val filesDir = context.filesDir

            val dir = File(filesDir, "images")
            dir.mkdirs()

            return File(dir, newName)
        }
    }
}