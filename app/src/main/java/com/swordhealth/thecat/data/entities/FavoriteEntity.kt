package com.swordhealth.thecat.data.entities

import com.google.gson.annotations.SerializedName

data class FavoriteEntity(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("sub_id")
    val subId: String? = null,

    @SerializedName("created_at")
    val createdAt: String? = null,

    @SerializedName("image")
    val image: ImageEntity? = null
)