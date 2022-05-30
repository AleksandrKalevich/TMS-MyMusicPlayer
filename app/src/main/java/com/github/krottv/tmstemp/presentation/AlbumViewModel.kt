package com.github.krottv.tmstemp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.krottv.tmstemp.data.AlbumsRepository
import com.github.krottv.tmstemp.data.db.AlbumDbInMemoryDataSource
import com.github.krottv.tmstemp.data.remote.AlbumRemoteDataSourceRetrofit
import com.github.krottv.tmstemp.domain.AlbumModel
import com.github.krottv.tmstemp.domain.ContentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class AlbumViewModel(private val albumRepository: AlbumsRepository): ViewModel() {

    private val _state = MutableStateFlow<Result<List<AlbumModel>>?>(null)
    val state: StateFlow<Result<List<AlbumModel>>?> = _state

    private var downloadingJob: Job? = null

    fun loadData(contentType: ContentType) {
        downloadingJob?.cancel()

        downloadingJob = viewModelScope.launch(Dispatchers.IO) {
            val result = try {
                Result.success(albumRepository.getAlbums(contentType))
            } catch (exception: Throwable) {
                Result.failure(exception)
            }

            _state.emit(result)
        }
    }
}
