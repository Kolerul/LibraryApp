package com.example.libraryapp.data.network.model
import com.google.gson.annotations.SerializedName

data class AccessInfo(
    @SerializedName("country") val country: String,
    @SerializedName("viewability") val viewability: String,
    @SerializedName("embeddable") val embeddable: Boolean,
    @SerializedName("publicDomain") val publicDomain: String,
    @SerializedName("textToSpeechPermission") val textToSpeechPermission: String,
    @SerializedName("epub") val epub: Epub,
    @SerializedName("pdf") val pdf: Pdf,
    @SerializedName("webReaderLink") val webReaderLink: String,
    @SerializedName("accessViewStatus") val accessViewStatus: String,
    @SerializedName("quoteSharingAllowed") val quoteSharingAllowed: Boolean
)