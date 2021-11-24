package com.kang.chan_ce

data class StoreData(
    val storeImage : String? = null,
    val storeName : String? = null,
    val storeLocation : String? =null,
    val storeTime : String? = null,
    val storeIntro: String? = null,
    val storeMenu: Map<String, String>? = null
)
