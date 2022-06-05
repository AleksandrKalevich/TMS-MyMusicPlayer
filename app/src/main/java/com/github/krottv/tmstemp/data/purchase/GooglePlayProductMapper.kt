package com.github.krottv.tmstemp.data.purchase

import com.android.billingclient.api.ProductDetails
import com.github.krottv.tmstemp.domain.purchase.PeriodType
import com.github.krottv.tmstemp.domain.purchase.ProductEntity

class GooglePlayProductMapper: Mapper<ProductDetails,ProductEntity> {
    override fun map(from: ProductDetails): ProductEntity {
        return ProductEntity(PeriodType.MONTH, from.oneTimePurchaseOfferDetails!!.formattedPrice, from)
    }
}