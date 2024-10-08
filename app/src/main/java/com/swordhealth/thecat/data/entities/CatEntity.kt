package com.swordhealth.thecat.data.entities

import com.google.gson.annotations.SerializedName

data class CatEntity(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("width")
    val width: Int? = null,

    @SerializedName("height")
    val height: Int? = null,

    @SerializedName("url")
    val url: String? = null,

    @SerializedName("breeds")
    val breeds: List<BreedEntity>? = null
) {
    data class BreedEntity(
        @SerializedName("weight")
        val weight: WeightEntity? = null,

        @SerializedName("id")
        val id: String? = null,

        @SerializedName("name")
        val name: String? = null,

        @SerializedName("temperament")
        val temperament: String? = null,

        @SerializedName("origin")
        val origin: String? = null,

        @SerializedName("country_codes")
        val countryCodes: String? = null,

        @SerializedName("country_code")
        val countryCode: String? = null,

        @SerializedName("life_span")
        val lifeSpan: String? = null,

        @SerializedName("wikipedia_url")
        val wikipediaUrl: String? = null
    ) {
        data class WeightEntity(
            @SerializedName("imperial")
            val imperial: String? = null,

            @SerializedName("metric")
            val metric: String? = null
        )
    }
}
