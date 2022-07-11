package com.github.krottv.tmstemp.data.remote

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SongUploadFake: SongUpload {

    override suspend fun downloadSong(url: String, saveFilePath: String): Flow<Float> {
        return flow {
            for (i in 0..100) {
                delay(100L)
                emit(i / 100f)
            }
        }
    }
}
