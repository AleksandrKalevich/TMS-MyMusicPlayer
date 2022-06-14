package com.github.krottv.tmstemp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.github.krottv.tmstemp.R
import com.github.krottv.tmstemp.binder.PurchaseFragmentBinder
import com.github.krottv.tmstemp.domain.purchase.PurchaseMakeInteractor
import com.github.krottv.tmstemp.presentation.PurchaseViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PurchaseFragment: Fragment() {

    private lateinit var binding: PurchaseFragmentBinder
    private val viewModel: PurchaseViewModel by sharedViewModel()
    private val purchaseMake: PurchaseMakeInteractor by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PurchaseFragmentBinder(this) {
            lifecycleScope.launch {
                purchaseMake.makePurchase(it)
            }
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.host_container, HostFragment())
                .commit()
        }

        return binding.bindView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadData()
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    if (it != null) {
                        if (it.isSuccess) {
                            binding.onDataLoaded(it.getOrThrow())
                        }
                    }
                }
            }
        }
    }
}