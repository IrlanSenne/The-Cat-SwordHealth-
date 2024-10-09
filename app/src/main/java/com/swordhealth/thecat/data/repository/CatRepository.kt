package com.swordhealth.thecat.data.repository

import androidx.paging.PagingData
import com.swordhealth.thecat.data.entities.CatEntity
import com.swordhealth.thecat.data.entities.FavoriteEntity
import com.swordhealth.thecat.data.entities.ImageEntity
import kotlinx.coroutines.flow.Flow

interface CatRepository {
    fun getCatsPaging(): Flow<PagingData<CatEntity>>
    fun searchCatsPaging(search: String): Flow<List<CatEntity>>
    fun getFavorites(): Flow<List<FavoriteEntity>>
    fun setAsFavorite(imageId: String): Flow<ImageEntity?>
}