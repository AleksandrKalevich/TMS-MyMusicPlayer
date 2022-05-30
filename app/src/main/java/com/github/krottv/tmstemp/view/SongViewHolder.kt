package com.github.krottv.tmstemp.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.krottv.tmstemp.R

class SongViewHolder (view: View): RecyclerView.ViewHolder(view) {
    val songImage: ImageView = view.findViewById(R.id.songImage)
    val songTitle: TextView = view.findViewById(R.id.songName)
    val songArtist: TextView = view.findViewById(R.id.songArtist)
}