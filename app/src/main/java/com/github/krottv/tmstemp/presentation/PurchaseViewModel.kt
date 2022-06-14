package com.github.krottv.tmstemp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.krottv.tmstemp.domain.purchase.ProductEntity
import com.github.krottv.tmstemp.domain.purchase.PurchaseStateInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PurchaseViewModel(private val purchaseState: PurchaseStateInteractor): ViewModel() {

    private val _state = MutableStateFlow<Result<List<ProductEntity>>?>(null)
    val state: StateFlow<Result<List<ProductEntity>>?> = _state

    private var downloadingJob: Job? = null

    fun loadData() {
        downloadingJob?.cancel()

        downloadingJob = viewModelScope.launch(Dispatchers.IO + Job()) {
            val result = try {
                Result.success(purchaseState.getListPurchases())
            } catch (exception: Throwable) {
                Result.failure(exception)
            }

            _state.emit(result)
        }
    }
}