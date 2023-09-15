package com.example.libraryapp.data.network.model

import com.google.gson.annotations.SerializedName

data class Pdf(
    @SerializedName("isAvialable") val isAvailable: String
)