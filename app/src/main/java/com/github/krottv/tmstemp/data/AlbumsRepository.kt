package com.github.krottv.tmstemp.data

import com.github.krottv.tmstemp.data.db.AlbumDbDataSource
import com.github.krottv.tmstemp.data.remote.AlbumRemoteDataSource
import com.github.krottv.tmstemp.domain.AlbumModel
import com.github.krottv.tmstemp.domain.ContentType

class AlbumsRepository(private val albumDbDataSource: AlbumDbDataSource,
                       private val albumRemoteDataSource: AlbumRemoteDataSource) {

    suspend fun getAlbums(contentType: ContentType): List<AlbumModel> {

        val cache = albumDbDataSource.getAlbums()
        val response = albumRemoteDataSource.getAlbums(contentType)

        if (cache == null) {
            albumDbDataSource.saveAlbums(contentType, response)
            return response
        }

        return if (cache.contains(contentType)) {
            cache[contentType]!!
        } else {
            albumDbDataSource.saveAlbums(contentType, response)
            response
        }
    }
}