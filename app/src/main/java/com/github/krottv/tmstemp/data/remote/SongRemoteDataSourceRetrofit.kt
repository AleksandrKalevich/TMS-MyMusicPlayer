package com.github.krottv.tmstemp.data.remote

import com.github.krottv.tmstemp.domain.AlbumType
import com.github.krottv.tmstemp.domain.ContentType
import com.github.krottv.tmstemp.domain.SongsJSON

class SongRemoteDataSourceRetrofit(private val api: RetrofitBuilder<MusicApi>) : SongRemoteDataSource {

    override suspend fun getSongs(albumType: AlbumType): SongsJSON {
        return when(albumType.contentType) {
            ContentType.ITUNES -> api.getApi().getItunesSongs(albumType.albumId)
            ContentType.LIBRARY -> api.getApi().getLibrarySongs(albumType.albumId)
            else -> { throw IllegalAccessError("ContentType Mismatch") }
        }
    }
}
