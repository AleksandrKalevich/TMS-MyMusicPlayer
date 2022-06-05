package com.github.krottv.tmstemp.worker.upload

import kotlinx.coroutines.flow.Flow

interface SongUploader {
    fun uploadImage(path: String): Flow<Float>
}