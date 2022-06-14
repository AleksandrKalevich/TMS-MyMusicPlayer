package com.github.krottv.tmstemp.binder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.krottv.tmstemp.databinding.SongsFragmentBinding
import com.github.krottv.tmstemp.domain.SongModel
import com.github.krottv.tmstemp.view.SongAdapter

class SongFragmentBinder(private val fragment: Fragment) {

    private lateinit var binding: SongsFragmentBinding

    fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SongsFragmentBinding.inflate(inflater, container, false)

        binding.songsContainer.layoutManager = LinearLayoutManager(fragment.requireContext())

        return binding.root
    }

    fun onDataLoaded(data: List<SongModel>) {

        if (binding.songsContainer.adapter == null) {
            binding.songsContainer.adapter = SongAdapter(data)
        } else {
            (binding.songsContainer.adapter as SongAdapter).data = data
        }
    }
}