package com.github.krottv.tmstemp.domain.purchase

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PurchaseStateInteractorFake: PurchaseStateInteractor {


    val _isPremium = MutableStateFlow(false)
    override val isPremium: StateFlow<Boolean>  = _isPremium

    override suspend fun getListPurchases(): List<ProductEntity> {
        val productList = ArrayList<ProductEntity>()
        productList.add(ProductEntity(PeriodType.MONTH, "10", false))
        productList.add(ProductEntity(PeriodType.YEAR, "100", false))

        return productList
    }
}