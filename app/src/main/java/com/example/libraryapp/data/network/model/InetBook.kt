package com.example.libraryapp.data.network.model

import com.google.gson.annotations.SerializedName

data class InetBook(
    @SerializedName("kind") val kind: String,
    @SerializedName("id") val id: String,
    @SerializedName("etag") val tag: String,
    @SerializedName("selfLink") val selfLink: String,
    @SerializedName("volumeInfo") val volumeInfo: InetVolume,
    @SerializedName("saleInfo") val saleInfo: SaleInfo,
    @SerializedName("accessInfo") val accessInfo: AccessInfo,
    @SerializedName("searchInfo") val searchInfo: SearchInfo
)