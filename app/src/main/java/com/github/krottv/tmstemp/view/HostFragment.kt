package com.github.krottv.tmstemp.view

import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.github.krottv.tmstemp.R
import com.github.krottv.tmstemp.data.permissions.PermissionState
import com.github.krottv.tmstemp.data.permissions.StoragePermissionChecker
import com.github.krottv.tmstemp.data.permissions.StoragePermissionCheckerImpl
import com.github.krottv.tmstemp.databinding.HostFragmentBinding
import com.github.krottv.tmstemp.domain.AlbumType
import com.github.krottv.tmstemp.domain.ContentType
import com.github.krottv.tmstemp.domain.purchase.PurchaseStateInteractor
import com.github.krottv.tmstemp.presentation.AlbumViewModel
import com.github.krottv.tmstemp.presentation.SongViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HostFragment : Fragment() {

    private lateinit var fragment: HostFragmentBinding
    private val sharedPreferences: SharedPreferences by inject()
    private val albumsFragment = AlbumsFragment()
    private val songsFragment = SongsFragment()
    private val myMusicFragment = SongsFragment()
    private val purchaseStateInteractor: PurchaseStateInteractor by inject()
    private val albumViewModel: AlbumViewModel by sharedViewModel()
    private val songViewModel: SongViewModel by sharedViewModel()
    private lateinit var storagePermissionChecker: StoragePermissionChecker

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragment = HostFragmentBinding.inflate(inflater)

        storagePermissionChecker = StoragePermissionCheckerImpl(requireActivity())
        return fragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment.iTunes.setOnClickListener {
            changeCurrentSelection(ContentType.ITUNES)
        }

        fragment.library.setOnClickListener {
            changeCurrentSelection(ContentType.LIBRARY)
        }

        fragment.myMusic.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                setPrimary(fragment.myMusic)
                viewLifecycleOwner.lifecycleScope.launch {
                    storagePermissionChecker.storagePermission.collectLatest { permissionState ->
                        when (permissionState) {
                            PermissionState.HAS_PERMISSION -> {
                                parentFragmentManager
                                    .beginTransaction()
                                    .remove(albumsFragment)
                                    .replace(R.id.songs_container, myMusicFragment)
                                    .commit()
                                songViewModel.loadMyData()
                            }
                            PermissionState.NO_PERMISSION -> {
                                storagePermissionChecker.startPermissionDialog()
                            }
                            PermissionState.REJECTED_ASK_AGAIN -> {
                                storagePermissionChecker.startPermissionDialog()
                            }
                            PermissionState.REJECTED_FOREVER -> {
                                fragment.myMusic.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            purchaseStateInteractor.isPremium.collect() {
                if (it) fragment.purchase.visibility = View.GONE
                else fragment.purchase.apply {
                    visibility = View.VISIBLE
                    setOnClickListener {
                        openFrag(PurchaseFragment(), R.id.host_container)
                    }
                }
            }
        }

        val currentContentType = when (sharedPreferences.getString("primaryTextView", "ITunes")) {
            "ITunes" -> ContentType.ITUNES
            "Library" -> ContentType.LIBRARY
            "My Music" -> ContentType.MY_MUSIC
            else -> { throw IllegalStateException() }
        }

        val albumFragmentBundle = Bundle()
        albumFragmentBundle.putSerializable("contentType", currentContentType)
        albumsFragment.arguments = albumFragmentBundle

        changeCurrentSelection(currentContentType)
    }

    private fun openFrag(fragment: Fragment, idHolder: Int) {
        parentFragmentManager
            .beginTransaction()
            .replace(idHolder, fragment)
            .commit()
    }

    private fun changeCurrentSelection(contentType: ContentType) {
        openFrag(albumsFragment, R.id.albums_container)
        openFrag(songsFragment, R.id.songs_container)
        albumsFragment.requireArguments().putSerializable("contentType", contentType)
        albumViewModel.loadData(contentType)
        songViewModel.loadData(AlbumType(1, contentType))

        when (contentType) {
            ContentType.ITUNES -> setPrimary(fragment.iTunes)
            ContentType.LIBRARY -> setPrimary(fragment.library)
            ContentType.MY_MUSIC -> setPrimary(fragment.myMusic)
        }
    }

    private fun setPrimary(primary: TextView) {
        primary.run {
            typeface = Typeface.DEFAULT_BOLD
            textSize = 18f
            setTextColor(ContextCompat.getColor(requireContext(), R.color.selectedTextColor))
            isClickable = false
        }

        setSecondary(primary)

        sharedPreferences.edit()
            .putString("primaryTextView", "${primary.text}")
            .apply()
    }

    private fun setSecondary(primary: TextView) {

        val textViewList = listOf(fragment.iTunes, fragment.library, fragment.myMusic)

        for (secondary in textViewList) {
            if (secondary != primary) {
                secondary.apply {
                    typeface = Typeface.create("sans-serif-light", Typeface.NORMAL)
                    textSize = 16f
                    setTextColor(ContextCompat.getColor(requireContext(), R.color.textColor))
                    isClickable = true
                }
            }
        }
    }
}
