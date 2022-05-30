package com.github.krottv.tmstemp.domain

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep @Serializable
data class AlbumModel(val id: Long, val image: String, val name: String, val trackCount: Int) {
}