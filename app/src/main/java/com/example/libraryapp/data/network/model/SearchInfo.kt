package com.example.libraryapp.data.network.model

import com.google.gson.annotations.SerializedName

data class SearchInfo(
    @SerializedName("textSnippet") val textSnippet: String
)