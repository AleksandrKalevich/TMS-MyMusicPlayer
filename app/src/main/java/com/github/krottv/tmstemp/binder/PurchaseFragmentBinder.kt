package com.github.krottv.tmstemp.binder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.krottv.tmstemp.databinding.PurchaseFragmentBinding
import com.github.krottv.tmstemp.domain.purchase.ProductEntity
import com.github.krottv.tmstemp.view.PurchaseAdapter

class PurchaseFragmentBinder(private val fragment: Fragment, private val onItemClick: (ProductEntity) -> Unit) {

    private lateinit var binding: PurchaseFragmentBinding

    fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PurchaseFragmentBinding.inflate(inflater, container, false)

        binding.purchasesContainer.layoutManager = LinearLayoutManager(fragment.requireContext())

        return binding.root
    }

    fun onDataLoaded(data: List<ProductEntity>) {

        if (binding.purchasesContainer.adapter == null) {
            binding.purchasesContainer.adapter = PurchaseAdapter(data, onItemClick)
        } else {
            (binding.purchasesContainer.adapter as PurchaseAdapter).data = data
        }
    }
}