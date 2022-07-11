package com.github.krottv.tmstemp.data.local

import com.github.krottv.tmstemp.domain.SongModel

interface TracksMyMusicDataSource {
    suspend fun getTracks(albumId: Long): List<SongModel>
}