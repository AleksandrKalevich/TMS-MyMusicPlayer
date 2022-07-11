package com.github.krottv.tmstemp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.krottv.tmstemp.data.AlbumsRepository
import com.github.krottv.tmstemp.data.local.AlbumsMyMusicDataSource
import com.github.krottv.tmstemp.domain.AlbumModel
import com.github.krottv.tmstemp.domain.ContentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AlbumViewModel(private val albumRepository: AlbumsRepository, private val albumsMyMusicDataSource: AlbumsMyMusicDataSource): ViewModel() {

    private val _state = MutableStateFlow<Result<List<AlbumModel>>?>(null)
    val state: StateFlow<Result<List<AlbumModel>>?> = _state

    private var downloadingJob: Job? = null

    fun loadData(contentType: ContentType) {
        downloadingJob?.cancel()

        downloadingJob = viewModelScope.launch(Dispatchers.IO) {
            val result = try {
                when (contentType)
                {
                    ContentType.MY_MUSIC -> Result.success(albumsMyMusicDataSource.getTracks())
                    else -> { Result.success(albumRepository.getAlbums(contentType)) }
                }
            } catch (exception: Throwable) {
                Result.failure(exception)
            }
            _state.emit(result)
        }
    }
}

