package com.github.krottv.tmstemp.worker.grayscale

import android.graphics.Bitmap

interface ImageSaver {
    fun saveImage(bitmap: Bitmap, newName: String): String
}