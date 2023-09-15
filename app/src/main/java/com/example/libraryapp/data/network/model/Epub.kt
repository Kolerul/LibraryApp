package com.example.libraryapp.data.network.model

import com.google.gson.annotations.SerializedName

data class Epub (
    @SerializedName("isAvailable") val isAvailable: Boolean
)