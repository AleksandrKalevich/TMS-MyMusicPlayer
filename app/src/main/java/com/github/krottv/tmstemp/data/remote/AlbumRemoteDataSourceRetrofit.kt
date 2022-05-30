package com.github.krottv.tmstemp.data.remote

import com.github.krottv.tmstemp.domain.AlbumModel
import com.github.krottv.tmstemp.domain.ContentType

class AlbumRemoteDataSourceRetrofit : AlbumRemoteDataSource {

    override suspend fun getAlbums(contentType: ContentType): List<AlbumModel> {
        return when(contentType) {
            ContentType.ITUNES -> RetrofitBuilder.musicApi.getItunesAlbums()
            ContentType.LIBRARY -> RetrofitBuilder.musicApi.getLibraryAlbums()
        }
    }
}