package com.github.krottv.tmstemp.domain.purchase

interface PurchaseMakeInteractor {
    suspend fun makePurchase(product: ProductEntity)
}