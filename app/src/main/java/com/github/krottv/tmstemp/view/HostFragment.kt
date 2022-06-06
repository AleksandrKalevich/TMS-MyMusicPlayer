package com.github.krottv.tmstemp.view

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.krottv.tmstemp.R
import com.github.krottv.tmstemp.databinding.HostFragmentBinding
import com.github.krottv.tmstemp.domain.AlbumType
import com.github.krottv.tmstemp.domain.ContentType
import com.github.krottv.tmstemp.presentation.AlbumViewModel
import com.github.krottv.tmstemp.presentation.SongViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HostFragment: Fragment() {

    private lateinit var fragment: HostFragmentBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val albumsFragment = AlbumsFragment()
    private val songsFragment = SongsFragment()
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

        var currentContentType = ContentType.ITUNES

        sharedPreferences = requireContext().getSharedPreferences("preferences", Context.MODE_PRIVATE)
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

        fragment.purchase.setOnClickListener {
            openFrag(PurchaseFragment(), R.id.host_container)
        }
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
