package com.github.krottv.tmstemp.utils

import android.content.res.Resources
import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView

object ChangeViewForm {
    fun setRoundRect(imageView: ImageView, dp: Int) {
        imageView.clipToOutline = true
        imageView.outlineProvider = object: ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setRoundRect(0, 0, view.width, view.height, dpToPx(dp))
            }
        }
    }

    private fun dpToPx(dp: Int): Float = dp * Resources.getSystem().displayMetrics.density
}