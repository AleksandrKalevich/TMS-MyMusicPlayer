package com.github.krottv.tmstemp.data.remote

interface RetrofitBuilder<ApiType> {
    fun getApi(): ApiType
}