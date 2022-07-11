package com.github.krottv.tmstemp.data.remote

import kotlinx.coroutines.flow.Flow

interface SongUpload {
    suspend fun downloadSong(url: String, saveFilePath: String): Flow<Float>
}