package com.swordhealth.thecat.data.entities

import com.google.gson.annotations.SerializedName

data class CatEntity(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("origin")
    val origin: String? = null,

    @SerializedName("temperament")
    val temperament: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("image")
    val image: ImageEntity? = null
)