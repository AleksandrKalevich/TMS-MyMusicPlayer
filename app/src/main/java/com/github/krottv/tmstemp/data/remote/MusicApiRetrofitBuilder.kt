package com.github.krottv.tmstemp.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create

class MusicApiRetrofitBuilder: RetrofitBuilder <MusicApi> {
    override fun getApi(): MusicApi {
        return Retrofit.Builder()
            .baseUrl("https://us-central1-inspiry-2ee60.cloudfunctions.net/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create()
    }
}