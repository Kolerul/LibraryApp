package com.example.libraryapp.data.network.model

import com.google.gson.annotations.SerializedName

data class IndustryIdentifiers(
    @SerializedName("type") val type: String,
    @SerializedName("identifier") val identifiers: String
)