package com.github.krottv.tmstemp.data.purchase

interface Mapper<FROM, TO> {
    fun map(from: FROM): TO
}