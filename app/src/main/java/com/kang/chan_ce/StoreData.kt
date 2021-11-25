package com.kang.chan_ce

data class StoreData(
    val storeImage : String? = null,
    val storeName : String? = null,
    val storeLocation : String? =null,
    val storeTime : String? = null,
    val storeIntro: String? = null,
    val storeNum: String? = null,
    val storeMenu: ArrayList<Map<String, String>>? = null,
    val xlatitude: String? = null,
    val ylatitude: String? = null
)
