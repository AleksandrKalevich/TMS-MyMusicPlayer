package com.github.krottv.tmstemp.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.work.WorkManager
import coil.load
import com.github.krottv.tmstemp.R
import com.github.krottv.tmstemp.data.worker.SongUploaderStarter
import com.github.krottv.tmstemp.domain.SongModel
import com.github.krottv.tmstemp.utils.ChangeViewForm.setRoundRect

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
            load(cell.image) {
                placeholder(R.drawable.loading)
            }
            scaleType = ImageView.ScaleType.CENTER_CROP
            setRoundRect(this, 8)
        }

        holder.itemView.setOnLongClickListener {
            SongUploaderStarter(WorkManager.getInstance()).start(cell.url, "/storage/emulated/0/Music/${cell.artist} - ${cell.title}.mp3")
            true
        }

        holder.songTitle.text = cell.title
        holder.songArtist.text = cell.artist
    }

    override fun getItemCount(): Int {
        return data.size
    }
}