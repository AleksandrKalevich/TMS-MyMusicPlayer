package com.github.krottv.tmstemp.view.mymus

import com.github.krottv.tmstemp.domain.AlbumModel

interface AlbumsMyMusicDataSource {
    suspend fun getTracks(): List<AlbumModel>
}