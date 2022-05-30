package com.github.krottv.tmstemp.data.remote

import com.github.krottv.tmstemp.domain.AlbumType
import com.github.krottv.tmstemp.domain.ContentType
import com.github.krottv.tmstemp.domain.SongsJSON

class SongRemoteDataSourceRetrofit : SongRemoteDataSource {

    override suspend fun getSongs(albumType: AlbumType): SongsJSON {
        return when(albumType.contentType) {
            ContentType.ITUNES -> RetrofitBuilder.musicApi.getItunesSongs(albumType.albumId)
            ContentType.LIBRARY -> RetrofitBuilder.musicApi.getLibrarySongs(albumType.albumId)
        }
    }
}
