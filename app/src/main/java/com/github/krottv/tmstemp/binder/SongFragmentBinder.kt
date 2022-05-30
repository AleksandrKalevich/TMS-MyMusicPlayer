package com.github.krottv.tmstemp.binder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.krottv.tmstemp.R
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

        return binding.root
    }

    fun onDataLoaded(data: List<SongModel>) {
        val recyclerView = binding.root.findViewById<RecyclerView>(R.id.songsContainer)

        recyclerView.layoutManager = LinearLayoutManager(fragment.requireContext())

        if (recyclerView.adapter == null) {
            recyclerView.adapter = SongAdapter(data)
        } else {
            (recyclerView.adapter as SongAdapter).data = data
        }
    }
}