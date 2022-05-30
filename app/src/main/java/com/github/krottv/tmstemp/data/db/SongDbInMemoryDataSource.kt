package com.github.krottv.tmstemp.data.db

import com.github.krottv.tmstemp.domain.AlbumType
import com.github.krottv.tmstemp.domain.ContentType
import com.github.krottv.tmstemp.domain.SongModel
import com.github.krottv.tmstemp.domain.SongsJSON

class SongDbInMemoryDataSource : SongDbDataSource {

    private var inMemoryData: MutableMap<AlbumType, List<SongModel>>? = null

    override suspend fun saveSongs(list: SongsJSON, contentType: ContentType) {
        if (inMemoryData == null) {
            inMemoryData = mutableMapOf(AlbumType(list.album.id, contentType) to list.tracks.map {it.copy(title = it.title + " (cache)")})
        } else {
            inMemoryData!![AlbumType(list.album.id, contentType)] = list.tracks.map {it.copy(title = it.title + " (cache)")}
        }
    }

    override suspend fun getSongs(): Map<AlbumType, List<SongModel>>? {
        return inMemoryData
    }
}