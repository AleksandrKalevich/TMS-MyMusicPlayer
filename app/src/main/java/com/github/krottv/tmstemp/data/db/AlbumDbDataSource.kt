package com.github.krottv.tmstemp.data.db

import com.github.krottv.tmstemp.domain.AlbumModel
import com.github.krottv.tmstemp.domain.ContentType

interface AlbumDbDataSource {
    suspend fun saveAlbums(contentType: ContentType, list: List<AlbumModel>)
    suspend fun getAlbums(): Map<ContentType, List<AlbumModel>>?
}