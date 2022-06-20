package com.github.krottv.tmstemp.view.mymus

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.github.krottv.tmstemp.domain.SongModel

class TracksMyMusicDataSourceImpl(private val context: Context) :
    TracksMyMusicDataSource {

    private val contentUriAlbums: Uri by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Audio.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL
            )
        } else {
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        }
    }

    override suspend fun getTracks(): List<SongModel> {

        val contentResolver = context.contentResolver!!

        val cursor = contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            arrayOf(
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media._ID
            ),
            null,
            null,
            MediaStore.Audio.Media.ALBUM + " asc"
        )

        val result = mutableListOf<SongModel>()
        if (cursor?.moveToFirst() == true) {

            do {
                val artist= cursor.getString(
                    cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
                )
                val id = cursor.getLong(
                    cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                )
                val title = cursor.getString(
                    cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
                )

                val image = ContentUris
                    .withAppendedId(contentUriAlbums, id)

                result.add(SongModel(artist, image.toString(), title, ""))

            } while (cursor.moveToNext())
        }
        return result
    }
}