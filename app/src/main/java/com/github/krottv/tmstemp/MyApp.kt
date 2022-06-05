package com.github.krottv.tmstemp

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.github.krottv.tmstemp.data.AlbumsRepository
import com.github.krottv.tmstemp.data.SongsRepository
import com.github.krottv.tmstemp.data.db.AlbumDbDataSource
import com.github.krottv.tmstemp.data.db.AlbumDbInMemoryDataSource
import com.github.krottv.tmstemp.data.db.SongDbDataSource
import com.github.krottv.tmstemp.data.db.SongDbInMemoryDataSource
import com.github.krottv.tmstemp.data.remote.*
import com.github.krottv.tmstemp.presentation.AlbumViewModel
import com.github.krottv.tmstemp.presentation.SongViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.androidx.workmanager.factory.KoinWorkerFactory
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


class MyApp : Application(), Configuration.Provider {

    private val moduleRepository: Module
        get() = module {
            factoryOf(::AlbumsRepository)
            factoryOf(::SongsRepository)

            singleOf(::MusicApiRetrofitBuilder) {
                bind<RetrofitBuilder<MusicApi>>()
            }

            factoryOf(::SongDbInMemoryDataSource) {
                bind<SongDbDataSource>()
            }

            factoryOf(::SongRemoteDataSourceRetrofit){
                bind<SongRemoteDataSource>()
            }

            factoryOf(::AlbumDbInMemoryDataSource){
                bind<AlbumDbDataSource>()
            }

            factoryOf(::AlbumRemoteDataSourceRetrofit){
                bind<AlbumRemoteDataSource>()
            }

            viewModelOf(::AlbumViewModel)
            viewModelOf(::SongViewModel)
        }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            module {
                modules(moduleRepository)
                androidContext(this@MyApp)
            }
        }

        WorkManager.initialize(this, workManagerConfiguration)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMaxSchedulerLimit(50)
            .setWorkerFactory(KoinWorkerFactory()).build()
    }
}