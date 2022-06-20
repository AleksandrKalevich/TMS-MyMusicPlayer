package com.github.krottv.tmstemp.view.mymus

import com.github.krottv.tmstemp.domain.SongModel

interface TracksMyMusicDataSource {
    suspend fun getTracks(): List<SongModel>
}