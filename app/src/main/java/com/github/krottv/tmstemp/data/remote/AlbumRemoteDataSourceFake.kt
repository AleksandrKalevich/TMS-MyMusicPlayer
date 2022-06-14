package com.github.krottv.tmstemp.data.remote

import com.github.krottv.tmstemp.domain.AlbumModel
import com.github.krottv.tmstemp.domain.ContentType

class AlbumRemoteDataSourceFake : AlbumRemoteDataSource {

    override suspend fun getAlbums(contentType: ContentType): List<AlbumModel> {
        return when(contentType) {
            ContentType.ITUNES -> getITunesAlbums()
            ContentType.LIBRARY -> getLibraryAlbums()
            ContentType.MY_MUSIC -> getLibraryAlbums()
        }
    }

    private fun getITunesAlbums(): List<AlbumModel> {
        val model = AlbumModel(
            0,
            "https://sun9-north.userapi.com/sun9-81/s/v1/ig2/wIL_XF8XNjbnczI7IwIec_w6FmZOxXPJLGrtMVEH7OnXTUqOpY2t_2Qt4wgHBECL8CipxHTG7bxwC35GPpsSMakE.jpg?size=1080x1054&quality=96&type=album",
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
            "https://sun9-west.userapi.com/sun9-47/s/v1/ig2/1lEJ8E-3rbUeX0ZPxQYK_JxtPJ5ZhTrGc58ZWzUm3tIJN-6cqoF2aC_SI8VXqvcyn2IrBBSdBwXpyXPULJJaWbQx.jpg?size=1080x814&quality=96&type=album",
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