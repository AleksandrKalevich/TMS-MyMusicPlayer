package com.github.krottv.tmstemp.binder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.krottv.tmstemp.databinding.AlbumsFragmentBinding
import com.github.krottv.tmstemp.domain.AlbumModel
import com.github.krottv.tmstemp.view.AlbumAdapter

class AlbumFragmentBinder(private val fragment: Fragment, private val onItemClick: (Long) -> Unit) {

    private lateinit var binding: AlbumsFragmentBinding

    fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AlbumsFragmentBinding.inflate(inflater, container, false)

        binding.container.layoutManager = LinearLayoutManager(fragment.requireContext(), RecyclerView.HORIZONTAL, false)

        return binding.root
    }

    fun onDataLoaded(data: List<AlbumModel>) {

        if (binding.container.adapter == null) {
            binding.container.adapter = AlbumAdapter(data, onItemClick)
        } else {
            (binding.container.adapter as AlbumAdapter).data = data
        }
    }
}