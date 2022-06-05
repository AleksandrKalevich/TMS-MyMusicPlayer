package com.github.krottv.tmstemp.worker.grayscale

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.github.krottv.tmstemp.R

class TestResourceImageGetterForWork(private val context: Context) : ImageGetterForWork {

    override suspend fun getImage(key: String): Bitmap {
        return BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher_background)
    }
}