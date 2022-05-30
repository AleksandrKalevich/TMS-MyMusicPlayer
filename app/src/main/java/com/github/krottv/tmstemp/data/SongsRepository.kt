package com.github.krottv.tmstemp.data

import com.github.krottv.tmstemp.data.db.SongDbDataSource
import com.github.krottv.tmstemp.data.remote.SongRemoteDataSource
import com.github.krottv.tmstemp.domain.AlbumType
import com.github.krottv.tmstemp.domain.SongModel

class SongsRepository(private val songDbDataSource: SongDbDataSource,
                      private val songRemoteDataSource: SongRemoteDataSource) {

    suspend fun getSongs(albumType: AlbumType): List<SongModel> {

        val cache = songDbDataSource.getSongs()
        val response = songRemoteDataSource.getSongs(albumType)

        if (cache == null) {
            songDbDataSource.saveSongs(response, albumType.contentType)
            return response.tracks
        }

        return if (cache.containsKey(albumType)) {
            cache[albumType]!!
        } else {
            songDbDataSource.saveSongs(response, albumType.contentType)
            response.tracks
        }
    }
}