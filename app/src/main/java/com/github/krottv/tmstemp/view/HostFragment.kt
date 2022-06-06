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
import com.github.krottv.tmstemp.databinding.HostFragmentBinding
import com.github.krottv.tmstemp.domain.AlbumType
import com.github.krottv.tmstemp.domain.ContentType
import com.github.krottv.tmstemp.domain.purchase.PurchaseStateInteractor
import com.github.krottv.tmstemp.presentation.AlbumViewModel
import com.github.krottv.tmstemp.presentation.SongViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HostFragment: Fragment() {

    private lateinit var fragment: HostFragmentBinding
    private val sharedPreferences: SharedPreferences by inject()
    private val albumsFragment = AlbumsFragment()
    private val songsFragment = SongsFragment()
    private val purchaseStateInteractor by inject<PurchaseStateInteractor>()
    private val albumViewModel by sharedViewModel <AlbumViewModel>()
    private val songViewModel by sharedViewModel <SongViewModel>()
    private val songFragmentBundle = Bundle()
    private val albumFragmentBundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragment = HostFragmentBinding.inflate(inflater)
        return fragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment.iTunes.setOnClickListener {
            albumFragmentBundle.putSerializable("contentType", ContentType.ITUNES)
            albumViewModel.loadData(ContentType.ITUNES)
            songViewModel.loadData(AlbumType(1,ContentType.ITUNES))
            changeCurrentSelection(fragment.iTunes, listOf(fragment.library, fragment.myMusic))
        }

        fragment.library.setOnClickListener {
            albumFragmentBundle.putSerializable("contentType", ContentType.LIBRARY)
            albumViewModel.loadData(ContentType.LIBRARY)
            songViewModel.loadData(AlbumType(1,ContentType.LIBRARY))
            changeCurrentSelection(fragment.library, listOf(fragment.iTunes, fragment.myMusic))
        }

        fragment.myMusic.setOnClickListener {
            changeCurrentSelection(fragment.myMusic, listOf(fragment.iTunes, fragment.library))
        }

        lifecycleScope.launch {
            purchaseStateInteractor.isPremium.collect() {
                if (it) fragment.purchase.visibility = View.GONE
                else fragment.purchase.apply {
                    visibility = View.VISIBLE
                    setOnClickListener {
                        openFrag(PurchaseFragment(), R.id.host_container)
                        parentFragmentManager
                            .beginTransaction()
                            .replace(R.id.host_container, PurchaseFragment(), "TAG")
                            .addToBackStack(null)
                            .commit()
                    }
                }
            }
        }

        var currentContentType = ContentType.ITUNES

        when (sharedPreferences.getString("primaryTextView", "ITunes")) {
            "ITunes" -> {
                changeCurrentSelection(fragment.iTunes, listOf(fragment.library, fragment.myMusic))
                currentContentType = ContentType.ITUNES
            }
            "Library" -> {
                changeCurrentSelection(fragment.library, listOf(fragment.iTunes, fragment.myMusic))
                currentContentType = ContentType.LIBRARY
            }
            "My Music" -> {
                changeCurrentSelection(fragment.myMusic, listOf(fragment.iTunes, fragment.library))
            }
        }

        songFragmentBundle.putLong("albumId", 1)
        songFragmentBundle.putSerializable("contentType", currentContentType)
        songsFragment.arguments = songFragmentBundle

        albumFragmentBundle.putSerializable("contentType", currentContentType)
        albumsFragment.arguments = albumFragmentBundle

        openFrag(albumsFragment, R.id.albums_container)
        openFrag(songsFragment, R.id.songs_container)

    }

    private fun openFrag(fragment: Fragment, idHolder: Int) {
        parentFragmentManager
            .beginTransaction()
            .replace(idHolder, fragment)
            .commit()
    }

    private fun changeCurrentSelection(primary: TextView, nonPrimary: List<TextView>) {
        setPrimary(primary)
        setSecondary(nonPrimary)
    }

    private fun setPrimary(primary: TextView) {
        primary.typeface = Typeface.DEFAULT_BOLD
        primary.textSize = 18f
        primary.setTextColor(ContextCompat.getColor(requireContext(), R.color.selectedTextColor))
        primary.isClickable = false

        sharedPreferences.edit()
            .putString("primaryTextView", "${primary.text}")
            .apply()
    }

    private fun setSecondary(nonPrimary: List<TextView>) {
        for (secondary in nonPrimary) {
            secondary.typeface = Typeface.create("sans-serif-light", Typeface.NORMAL)
            secondary.textSize = 16f
            secondary.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColor))
            secondary.isClickable = true
        }
    }
}
