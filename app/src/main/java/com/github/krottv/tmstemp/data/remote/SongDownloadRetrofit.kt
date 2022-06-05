package com.github.krottv.tmstemp.data.remote

import okhttp3.ResponseBody
import retrofit2.Response

class SongDownloadRetrofit(private val api: RetrofitBuilder<MusicApi>): SongDownload {
    override suspend fun downloadSong(url: String): Response<ResponseBody> {
        return api.getApi().downloadSong(url)
    }
}