package com.github.krottv.tmstemp.data.remote

import com.github.krottv.tmstemp.domain.AlbumModel
import com.github.krottv.tmstemp.domain.ContentType

interface AlbumRemoteDataSource {
    suspend fun getAlbums(contentType: ContentType): List<AlbumModel>
} 