package com.github.krottv.tmstemp.data.datastore

import android.content.Context
import com.github.krottv.tmstemp.domain.ContentType
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import java.io.File

class DataStoreCurrentContentTypeState(private val context: Context, private val json: Json) {

    fun getCurrentContentType(): ContentType {
        return try {
            getContentType()
        } catch (exception: Exception) {
            setCurrentContentType(ContentType.ITUNES)
            getContentType()
        }
    }

    fun setCurrentContentType(contentType: ContentType) {
        val file = getFileContentTypes()
        file.delete()

        val stream = file.outputStream()

        stream.use {
            json.encodeToStream(contentType, it)
        }
    }

    private fun getContentType(): ContentType {
        val file = getFileContentTypes()

        return file.inputStream().use {
            json.decodeFromStream(it)
        }
    }

    private fun getFileContentTypes(): File {

        val folder = File(context.filesDir, "currentTab")
        folder.mkdirs()

        return File(folder, "contentType.json")
    }
}

