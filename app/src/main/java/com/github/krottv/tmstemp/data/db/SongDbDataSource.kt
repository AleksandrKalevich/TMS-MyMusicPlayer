package com.github.krottv.tmstemp.data.db

import com.github.krottv.tmstemp.domain.AlbumType
import com.github.krottv.tmstemp.domain.ContentType
import com.github.krottv.tmstemp.domain.SongModel
import com.github.krottv.tmstemp.domain.SongsJSON

interface SongDbDataSource {
    suspend fun saveSongs(list: SongsJSON, contentType: ContentType)
    suspend fun getSongs(): Map<AlbumType, List<SongModel>>?
}