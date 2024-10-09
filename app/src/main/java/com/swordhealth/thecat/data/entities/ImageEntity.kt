package com.swordhealth.thecat.data.entities

import com.google.gson.annotations.SerializedName

data class ImageEntity(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("url")
    val url: String? = null
)
