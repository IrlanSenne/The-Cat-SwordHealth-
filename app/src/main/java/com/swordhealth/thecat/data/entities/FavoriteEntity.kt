package com.swordhealth.thecat.data.entities

import com.google.gson.annotations.SerializedName

data class FavoriteEntity(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("image")
    val image: ImageEntity? = null
)