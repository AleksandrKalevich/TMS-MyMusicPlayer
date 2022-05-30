package com.github.krottv.tmstemp.data.remote

import com.github.krottv.tmstemp.domain.AlbumType
import com.github.krottv.tmstemp.domain.SongsJSON

interface SongRemoteDataSource {
    suspend fun getSongs(albumType: AlbumType): SongsJSON
}