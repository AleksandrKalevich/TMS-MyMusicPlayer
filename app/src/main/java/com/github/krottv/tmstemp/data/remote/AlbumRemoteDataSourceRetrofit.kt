package com.github.krottv.tmstemp.data.remote

import com.github.krottv.tmstemp.domain.AlbumModel
import com.github.krottv.tmstemp.domain.ContentType

class AlbumRemoteDataSourceRetrofit(private val api: RetrofitBuilder<MusicApi>) : AlbumRemoteDataSource {

    override suspend fun getAlbums(contentType: ContentType): List<AlbumModel> {
        return when(contentType) {
            ContentType.ITUNES -> api.getApi().getItunesAlbums()
            ContentType.LIBRARY -> api.getApi().getLibraryAlbums()
            else -> { throw IllegalAccessError("ContentType Mismatch") }
        }
    }
}