package com.github.krottv.tmstemp.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.github.krottv.tmstemp.R
import com.github.krottv.tmstemp.domain.purchase.PeriodType
import com.github.krottv.tmstemp.domain.purchase.ProductEntity

class PurchaseAdapter(data: List<ProductEntity>, private val onItemClick: (ProductEntity) -> Unit): RecyclerView.Adapter<PurchaseViewHolder>() {

    var data: List<ProductEntity> = data
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.purchase_element, parent, false)
        return PurchaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: PurchaseViewHolder, position: Int) {
        val cell = data[position]

        holder.purchaseType.apply {
            text = cell.priceLocal
            setOnClickListener {
                when(cell.periodType) {
                    PeriodType.MONTH -> Toast.makeText(context, "Month subscription activated", Toast.LENGTH_SHORT).show()
                    PeriodType.YEAR -> Toast.makeText(context, "Year subscription activated", Toast.LENGTH_SHORT).show()
                }
                onItemClick(cell)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
