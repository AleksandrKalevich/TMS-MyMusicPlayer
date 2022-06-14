package com.github.krottv.tmstemp.presentation

import android.os.Build
import androidx.annotation.WorkerThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.krottv.tmstemp.data.remote.SongDownload
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection

class SongDownloadViewModel(private val songDownload: SongDownload) : ViewModel() {

    private var downloadingJob: Job? = null

    fun downloadSong(url: String, saveFilePath: String): Flow<Long> {
        downloadingJob?.cancel()

        downloadingJob = viewModelScope.launch {
            val responseBody = songDownload.downloadSong(url).body()
            saveFile(responseBody, saveFilePath)
        }

        return flow {
            val file = File(saveFilePath)
            val size = file.length()
            emit(size/getFileSizeOfUrl(url))
        }
    }

    @WorkerThread
    private fun getFileSizeOfUrl(url: String): Long {
        var urlConnection: URLConnection? = null
        try {
            val uri = URL(url)
            urlConnection = uri.openConnection()
            urlConnection!!.connect()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                return urlConnection.contentLengthLong
            val contentLengthStr = urlConnection.getHeaderField("content-length")
            return if (contentLengthStr.isNullOrEmpty()) -1 else contentLengthStr.toLong()
        } catch (ignored: Exception) {
        } finally {
            if (urlConnection is HttpURLConnection)
                urlConnection.disconnect()
        }
        return -1
    }

    private fun saveFile(body: ResponseBody?, pathWhereYouWantToSaveFile: String): String {
        if (body == null)
            return ""
        var input: InputStream? = null
        try {
            input = body.byteStream()
            val fos = FileOutputStream(pathWhereYouWantToSaveFile)
            fos.use { output ->
                val buffer = ByteArray(4 * 1024) // or other buffer size
                var read: Int
                while (input.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
            return pathWhereYouWantToSaveFile
        } catch (e: Exception) {}
        finally {
            input?.close()
        }
        return ""
    }
}