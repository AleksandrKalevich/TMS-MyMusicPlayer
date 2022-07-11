package com.github.krottv.tmstemp.presentation.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.krottv.tmstemp.R

class PurchaseViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val purchaseType: TextView = view.findViewById(R.id.purchaseType)
}