package com.github.krottv.tmstemp.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.krottv.tmstemp.R

class AlbumViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val albumImage: ImageView = view.findViewById(R.id.albumImage)
    val albumName: TextView = view.findViewById(R.id.albumName)
    val albumImageFrame: ImageView = view.findViewById(R.id.albumImageFrame)
}