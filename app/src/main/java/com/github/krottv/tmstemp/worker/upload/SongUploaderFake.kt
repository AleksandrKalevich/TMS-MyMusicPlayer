package com.github.krottv.tmstemp.worker.upload

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SongUploaderFake: SongUploader {

    override fun uploadImage(path: String): Flow<Float> {
        return flow {
            for (i in 0..100) {
                delay(100L)
                emit(i / 100f)
            }
        }
    }
}