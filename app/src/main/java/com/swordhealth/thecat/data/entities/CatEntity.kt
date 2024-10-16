package com.swordhealth.thecat.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Entity(tableName = "cats_list")
data class CatEntity(
    @SerializedName("id")
    val id: String,

    @PrimaryKey
    @SerializedName("name")
    val name: String,

    @SerializedName("origin")
    val origin: String? = null,

    @SerializedName("temperament")
    val temperament: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("life_span")
    val lifeSpan: String? = null,

    @SerializedName("image")
    val image: ImageEntity? = null,

    var idFavorite: String? = null,

    var isPendingSync : Boolean = false
)

@Serializable
data class CatUI(
    val id: String? = null,
    val name: String? = null,
    val origin: String? = null,
    val temperament: String? = null,
    val description: String? = null,
    val lifeSpan: String? = null,
    val image: ImageUI? = null,
    val idFavorite: String? = null,
    val isFromFavourites: Boolean? = false,
)