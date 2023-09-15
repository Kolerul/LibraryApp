package com.example.libraryapp.data.network.model

import com.google.gson.annotations.SerializedName

data class SaleInfo(
    @SerializedName("country") val country: String,
    @SerializedName("saleability") val saleability: String,
    @SerializedName("isBook") val isBook: Boolean
)