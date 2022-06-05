package com.github.krottv.tmstemp.worker.grayscale

import android.graphics.Bitmap

interface GrayscaleApplier {
    fun apply(bitmap: Bitmap): Bitmap
}