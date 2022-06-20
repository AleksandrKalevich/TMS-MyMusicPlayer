package com.github.krottv.tmstemp.data.remote

import com.github.krottv.tmstemp.domain.AlbumModel
import com.github.krottv.tmstemp.domain.ContentType

class AlbumRemoteDataSourceFake : AlbumRemoteDataSource {

    override suspend fun getAlbums(contentType: ContentType): List<AlbumModel> {
        return when(contentType) {
            ContentType.ITUNES -> getITunesAlbums()
            ContentType.LIBRARY -> getLibraryAlbums()
            else -> { throw IllegalAccessError("ContentType Mismatch") }
        }
    }

    private fun getITunesAlbums(): List<AlbumModel> {
        val model = AlbumModel(
            0,
            "https://sun9-west.userapi.com/sun9-15/s/v1/if1/clLrdLujJuK86SV17KmlzQE8QT70-yqAJ9vgzv9USJjNmzyWlC3pxLo5QGpx0htEZpOnbVkn.jpg?size=920x520&quality=96&type=album",
            "Album",
            10
        )

        val mutableListOf = ArrayList<AlbumModel>(10)
        for (i in 0..10) {
            mutableListOf.add(model.copy(name = "Alb${i+1}"))
        }

        return mutableListOf
    }

    private fun getLibraryAlbums(): List<AlbumModel> {
        val model = AlbumModel(
            0,
            "https://merchbar.imgix.net/product/105/6519/2520008982610/XW1Ogc4P34art.png?auto=format&fit=max&w=750",
            "Album",
            10
        )

        val mutableListOf = ArrayList<AlbumModel>(10)
        for (i in 0..10) {
            mutableListOf.add(model.copy(name = "Alb${i+1}"))
        }

        return mutableListOf
    }
}