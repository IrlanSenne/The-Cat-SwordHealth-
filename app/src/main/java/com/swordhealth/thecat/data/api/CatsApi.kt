package com.swordhealth.thecat.data.api

import com.swordhealth.thecat.data.entities.CatEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface CatsApi {

    @GET("breeds")
    suspend fun getCats(
        @Query("page") page: Int = 0,
        @Query("limit") limit: Int = 18,
    ): List<CatEntity>

    @GET("breeds/search")
    suspend fun searchCats(
        @Query("q") query: String,
        @Query("attach_image") attachImage: Int = 1
    ): List<CatEntity>
}

