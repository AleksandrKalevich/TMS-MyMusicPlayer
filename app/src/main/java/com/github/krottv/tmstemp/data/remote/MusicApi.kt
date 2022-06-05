package com.github.krottv.tmstemp.data.remote

import com.github.krottv.tmstemp.domain.AlbumModel
import com.github.krottv.tmstemp.domain.SongsJSON
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url

interface MusicApi {

    @Streaming
    @GET
    suspend fun downloadSong(@Url fileUrl: String): Response<ResponseBody>

    @GET("getItunesAlbums")
    suspend fun getItunesAlbums() : List<AlbumModel>

    @GET("getItunesTracks")
    suspend fun getItunesSongs(@Query("albumId") album_id: Long) : SongsJSON

    @GET("getLibraryAlbums")
    suspend fun getLibraryAlbums() : List<AlbumModel>

    @GET("getLibraryTracks")
    suspend fun getLibrarySongs(@Query("albumId") album_id: Long) : SongsJSON
}


