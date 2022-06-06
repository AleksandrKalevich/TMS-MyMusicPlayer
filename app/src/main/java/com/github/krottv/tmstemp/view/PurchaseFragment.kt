package com.github.krottv.tmstemp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.github.krottv.tmstemp.databinding.PurchasePageBinding
import com.github.krottv.tmstemp.domain.purchase.PeriodType
import com.github.krottv.tmstemp.domain.purchase.ProductEntity
import com.github.krottv.tmstemp.domain.purchase.PurchaseMakeInteractor
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class PurchaseFragment: Fragment() {

    private lateinit var binding: PurchasePageBinding
    private val purchaseMake by inject<PurchaseMakeInteractor>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PurchasePageBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.monthPurchare.setOnClickListener {
            lifecycleScope.launch {
                purchaseMake.makePurchase(ProductEntity(PeriodType.MONTH, "10", false))
            }
            parentFragmentManager.findFragmentByTag("TAG")
        }

        binding.yearPurchare.setOnClickListener {
            lifecycleScope.launch {
                purchaseMake.makePurchase(ProductEntity(PeriodType.YEAR, "10", false))
            }
            parentFragmentManager.popBackStackImmediate("TAG", 0)
        }
    }
}