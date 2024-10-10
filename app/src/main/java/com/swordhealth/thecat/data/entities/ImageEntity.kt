package com.swordhealth.thecat.data.entities

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class ImageEntity(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("width")
    val width: Int? = null,

    @SerializedName("height")
    val height: Int? = null,

    @SerializedName("url")
    val url: String? = null
)

@Serializable
data class ImageUI(
    val id: String? = null,
    val width: Int? = null,
    val height: Int? = null,
    val url: String? = null
)
