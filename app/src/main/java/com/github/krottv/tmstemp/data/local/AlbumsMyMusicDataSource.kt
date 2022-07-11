package com.github.krottv.tmstemp.data.local

import com.github.krottv.tmstemp.domain.AlbumModel

interface AlbumsMyMusicDataSource {
    suspend fun getTracks(): List<AlbumModel>
}