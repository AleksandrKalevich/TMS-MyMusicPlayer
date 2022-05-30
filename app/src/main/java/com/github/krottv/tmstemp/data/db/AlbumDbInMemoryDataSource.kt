package com.github.krottv.tmstemp.data.db

import com.github.krottv.tmstemp.domain.AlbumModel
import com.github.krottv.tmstemp.domain.ContentType

class AlbumDbInMemoryDataSource : AlbumDbDataSource {

    private var inMemoryData1: MutableMap<ContentType, List<AlbumModel>>? = null

    override suspend fun saveAlbums(contentType: ContentType, list: List<AlbumModel>) {
        if (inMemoryData1 == null) {
            inMemoryData1 = mutableMapOf(contentType to list.map {it.copy(name = it.name + " (cache)")})
        } else {
            inMemoryData1!![contentType] = list.map {it.copy(name = it.name + " (cache)")}
        }
    }

    override suspend fun getAlbums(): Map<ContentType, List<AlbumModel>>? {
        return inMemoryData1
    }
}