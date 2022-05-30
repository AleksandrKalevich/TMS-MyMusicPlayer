package com.github.krottv.tmstemp.view

import android.content.res.Resources
import android.graphics.Outline
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.github.krottv.tmstemp.R
import com.github.krottv.tmstemp.domain.AlbumModel

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
            onItemClick(position+1.toLong())
        }

        holder.albumName.text = cell.name
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private fun setRoundRect(imageView: ImageView, dp: Int) {
        imageView.clipToOutline = true
        imageView.outlineProvider = object: ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setRoundRect(0, 0, view.width, view.height, dpToPx(dp))
            }
        }
    }

    private fun dpToPx(dp: Int): Float = dp * Resources.getSystem().displayMetrics.density
}