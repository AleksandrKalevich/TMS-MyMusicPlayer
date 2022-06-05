package com.github.krottv.tmstemp.data.remote

import okhttp3.ResponseBody
import retrofit2.Response

interface SongDownload {
    suspend fun downloadSong(url: String): Response<ResponseBody>
}