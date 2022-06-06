package com.github.krottv.tmstemp.domain.purchase

class PurchaseMakerInteractorFake(private val purchaseStateInteractorFake: PurchaseStateInteractorFake): PurchaseMakeInteractor {

    override suspend fun makePurchase(product: ProductEntity) {
        purchaseStateInteractorFake._isPremium.value = true
    }
}