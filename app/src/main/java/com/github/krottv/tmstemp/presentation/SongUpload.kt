package com.github.krottv.tmstemp.presentation

import kotlinx.coroutines.flow.Flow

interface SongUpload {
    suspend fun downloadSong(url: String, saveFilePath: String): Flow<Float>
}