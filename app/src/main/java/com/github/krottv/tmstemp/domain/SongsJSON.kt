package com.github.krottv.tmstemp.domain

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep @Serializable
class SongsJSON (val album: AlbumModel, val tracks: List<SongModel>)