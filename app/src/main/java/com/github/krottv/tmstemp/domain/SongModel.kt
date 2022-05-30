package com.github.krottv.tmstemp.domain

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep @Serializable
data class SongModel(val artist: String, val image: String, val title: String, val url: String) {
}