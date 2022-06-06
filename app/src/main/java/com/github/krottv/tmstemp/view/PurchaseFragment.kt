package com.github.krottv.tmstemp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.krottv.tmstemp.R
import com.github.krottv.tmstemp.databinding.PurchasePageBinding

class PurchaseFragment: Fragment() {

    private lateinit var binding: PurchasePageBinding

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
            openFrag(HostFragment(), R.id.host_container)
        }

        binding.yearPurchare.setOnClickListener {
            openFrag(HostFragment(), R.id.host_container)
        }
    }

    private fun openFrag(fragment: Fragment, idHolder: Int) {
        parentFragmentManager
            .beginTransaction()
            .replace(idHolder, fragment)
            .commit()
    }
}