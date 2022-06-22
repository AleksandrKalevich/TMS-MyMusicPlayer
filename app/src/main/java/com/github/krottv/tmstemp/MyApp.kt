package com.github.krottv.tmstemp

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
import com.github.krottv.tmstemp.data.AlbumsRepository
import com.github.krottv.tmstemp.data.SongsRepository
import com.github.krottv.tmstemp.data.datastore.DataStoreCurrentContentTypeState
import com.github.krottv.tmstemp.data.db.AlbumDbDataSource
import com.github.krottv.tmstemp.data.db.AlbumDbInMemoryDataSource
import com.github.krottv.tmstemp.data.db.SongDbDataSource
import com.github.krottv.tmstemp.data.db.SongDbInMemoryDataSource
import com.github.krottv.tmstemp.data.permissions.StoragePermissionChecker
import com.github.krottv.tmstemp.data.permissions.StoragePermissionCheckerImpl
import com.github.krottv.tmstemp.data.remote.*
import com.github.krottv.tmstemp.domain.purchase.PurchaseMakeInteractor
import com.github.krottv.tmstemp.domain.purchase.PurchaseMakerInteractorFake
import com.github.krottv.tmstemp.domain.purchase.PurchaseStateInteractor
import com.github.krottv.tmstemp.domain.purchase.PurchaseStateInteractorFake
import com.github.krottv.tmstemp.presentation.*
import com.github.krottv.tmstemp.view.mymus.AlbumsMyMusicDataSource
import com.github.krottv.tmstemp.view.mymus.AlbumsMyMusicDataSourceImpl
import com.github.krottv.tmstemp.view.mymus.TracksMyMusicDataSource
import com.github.krottv.tmstemp.view.mymus.TracksMyMusicDataSourceImpl
import com.github.krottv.tmstemp.worker.upload.SongUploadWorker
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.androidx.workmanager.factory.KoinWorkerFactory
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

class MyApp : Application(), Configuration.Provider {

    private val moduleDataStore: Module
        get() = module {

            singleOf(::DataStoreCurrentContentTypeState)

            single<Json> { Json }
        }

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

            factoryOf(::SongRemoteDataSourceFake){
                bind<SongRemoteDataSource>()
            }

            factoryOf(::AlbumDbInMemoryDataSource){
                bind<AlbumDbDataSource>()
            }

            factoryOf(::AlbumRemoteDataSourceFake){
                bind<AlbumRemoteDataSource>()
            }

            viewModelOf(::AlbumViewModel)
            viewModelOf(::SongViewModel)
            viewModelOf(::PurchaseViewModel)
        }

    private val modulePurchases: Module
        get() = module {
            singleOf(::PurchaseMakerInteractorFake) {
                bind<PurchaseMakeInteractor>()
            }

            singleOf(::PurchaseStateInteractorFake) {
                bind<PurchaseStateInteractor>()
            }
        }

    private val moduleWorker: Module
        get() = module {

            workerOf(::SongUploadWorker)

            factoryOf(::SongUploadFake) {
                bind<SongUpload>()
            }

            factoryOf(::SongDownloadRetrofit) {
                bind<SongDownload>()
            }
        }

    private val moduleSharedPreferences: Module
        get() = module {
            single {
                val context: Context = get()
                context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
            }
        }

    private val moduleMusicFromDevice: Module
        get() = module {

            single<StoragePermissionChecker> { params -> StoragePermissionCheckerImpl(params.get()) }

            factoryOf(::AlbumsMyMusicDataSourceImpl) {
                bind<AlbumsMyMusicDataSource>()
            }

            factoryOf(::TracksMyMusicDataSourceImpl) {
                bind<TracksMyMusicDataSource>()
            }
        }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            module {
                modules(moduleRepository, modulePurchases, moduleSharedPreferences, moduleWorker, moduleMusicFromDevice, moduleDataStore)
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