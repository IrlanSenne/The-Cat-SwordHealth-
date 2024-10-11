package com.swordhealth.thecat.data.api

import com.swordhealth.thecat.data.entities.CatEntity
import com.swordhealth.thecat.data.entities.FavoriteEntity
import com.swordhealth.thecat.data.entities.FavoriteRequestDto
import com.swordhealth.thecat.data.entities.ImageEntity
import com.swordhealth.thecat.data.entities.SetAsFavouriteResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
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

    @GET("favourites")
    suspend fun getFavorites(): List<FavoriteEntity>

    @POST("favourites")
    suspend fun setFavorite(
        @Body favoriteRequestDto: FavoriteRequestDto
    ): SetAsFavouriteResponse

    @DELETE("favourites/{favourite_id}")
    suspend fun deleteFavorite(
        @Path("favourite_id") favoriteId: String
    ): SetAsFavouriteResponse
}
