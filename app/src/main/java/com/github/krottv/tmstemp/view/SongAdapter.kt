package com.github.krottv.tmstemp.view

import android.graphics.Outline
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.github.krottv.tmstemp.R
import com.github.krottv.tmstemp.domain.SongModel

class SongAdapter(data: List<SongModel>): RecyclerView.Adapter<SongViewHolder>() {

    var data: List<SongModel> = data
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.song_element, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val cell = data[position]

        holder.songImage.apply {
            load(cell.image.replace("{w}", "200").replace("{h}", "200")) {
                placeholder(R.drawable.loading)
            }
            scaleType = ImageView.ScaleType.CENTER_CROP

            clipToOutline = true

            outlineProvider = object: ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline) {
                    outline.setRoundRect(0,0,view.width, view.height, 20f)
                }
            }
        }
        holder.songTitle.text = cell.title
        holder.songArtist.text = cell.artist
    }

    override fun getItemCount(): Int {
        return data.size
    }
}