package com.swordhealth.thecat.data.api

import com.swordhealth.thecat.data.entities.CatEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface CatsApi {

    @GET("images/search")
    suspend fun getCats(
        @Query("page") page: Int = 0,
        @Query("limit") limit: Int = 18
    ): List<CatEntity>
}

