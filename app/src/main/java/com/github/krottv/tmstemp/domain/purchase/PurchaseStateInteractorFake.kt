package com.github.krottv.tmstemp.domain.purchase

import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PurchaseStateInteractorFake(private val sharedPreferences: SharedPreferences): PurchaseStateInteractor {

    val _isPremium = MutableStateFlow(sharedPreferences.getBoolean("isPurchased", false))
    override val isPremium: StateFlow<Boolean>  = _isPremium

    override suspend fun getListPurchases(): List<ProductEntity> {
        val productList = ArrayList<ProductEntity>()
        productList.add(ProductEntity(PeriodType.MONTH, "Подписка на месяц - 10$", false))
        productList.add(ProductEntity(PeriodType.YEAR, "Подписка на год - 100$", false))
        return productList
    }
}