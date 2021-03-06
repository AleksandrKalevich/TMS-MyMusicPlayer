package com.github.krottv.tmstemp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.github.krottv.tmstemp.domain.AlbumType
import com.github.krottv.tmstemp.domain.ContentType
import com.github.krottv.tmstemp.presentation.AlbumViewModel
import com.github.krottv.tmstemp.presentation.SongViewModel
import com.github.krottv.tmstemp.presentation.view.binder.AlbumFragmentBinder
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AlbumsFragment: Fragment() {

    private lateinit var binder: AlbumFragmentBinder
    private val albumViewModel: AlbumViewModel by sharedViewModel()
    private val songViewModel: SongViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binder = AlbumFragmentBinder(this) {
            songViewModel.loadData(AlbumType(it, requireArguments().get("contentType") as ContentType))
        }

        return binder.bindView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                albumViewModel.state.collect {
                    if (it != null) {
                        if (it.isSuccess) {
                            binder.onDataLoaded(it.getOrThrow())
                        }
                    }
                }
            }
        }
    }
}