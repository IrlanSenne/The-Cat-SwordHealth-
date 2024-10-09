package com.swordhealth.thecat.data.entities

import com.google.gson.annotations.SerializedName

data class FavoriteRequestDto(
    @SerializedName("image_id")
    val imageId: String,
    @SerializedName("sub_id")
    val subId: String? = null,
)