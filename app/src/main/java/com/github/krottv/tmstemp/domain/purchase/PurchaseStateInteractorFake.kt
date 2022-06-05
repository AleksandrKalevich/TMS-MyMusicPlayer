package com.github.krottv.tmstemp.domain.purchase

import kotlinx.coroutines.flow.StateFlow

class PurchaseStateInteractorFake: PurchaseStateInteractor {

    override val isPremium: StateFlow<Boolean>
        get() = TODO("Not yet implemented")

    override suspend fun getListPurchases(): List<ProductEntity> {
        TODO("Not yet implemented")
    }
}