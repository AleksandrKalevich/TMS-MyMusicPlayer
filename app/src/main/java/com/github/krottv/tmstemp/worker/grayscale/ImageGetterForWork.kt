package com.github.krottv.tmstemp.worker.grayscale

import android.graphics.Bitmap

interface ImageGetterForWork {
    suspend fun getImage(key: String): Bitmap
}