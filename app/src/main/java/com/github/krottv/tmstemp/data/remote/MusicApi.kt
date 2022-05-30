package com.github.krottv.tmstemp.data.remote

import com.github.krottv.tmstemp.domain.AlbumModel
import com.github.krottv.tmstemp.domain.SongsJSON
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

object RetrofitBuilder {
    val musicApi: MusicApi = Retrofit.Builder()
        .baseUrl("https://us-central1-inspiry-2ee60.cloudfunctions.net/")
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create()
}

interface MusicApi {

    @GET("getItunesAlbums")
    suspend fun getItunesAlbums() : List<AlbumModel>

    @GET("getItunesTracks")
    suspend fun getItunesSongs(@Query("albumId") album_id: Long) : SongsJSON

    @GET("getLibraryAlbums")
    suspend fun getLibraryAlbums() : List<AlbumModel>

    @GET("getLibraryTracks")
    suspend fun getLibrarySongs(@Query("albumId") album_id: Long) : SongsJSON
}


