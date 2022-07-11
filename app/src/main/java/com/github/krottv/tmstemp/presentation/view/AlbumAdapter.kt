package com.github.krottv.tmstemp.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.github.krottv.tmstemp.R
import com.github.krottv.tmstemp.domain.AlbumModel
import com.github.krottv.tmstemp.utils.ChangeViewForm.setRoundRect

class AlbumAdapter(data: List<AlbumModel>, private val onItemClick: (Long) -> Unit): RecyclerView.Adapter<AlbumViewHolder>() {

    var data: List<AlbumModel> = data
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.album_element, parent, false)
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val cell = data[position]

        holder.albumImage.apply {
            load(cell.image) {
                placeholder(R.drawable.loading)
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
            setRoundRect(this, 10)
        }

        holder.albumImageFrame.apply {
            setBackgroundResource(R.color.colorPrimary)
            setRoundRect(this, 12)
        }

        holder.itemView.setOnClickListener {
            onItemClick(cell.id)
        }

        holder.albumName.text = cell.name
    }

    override fun getItemCount(): Int {
        return data.size
    }
}