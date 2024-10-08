package com.swordhealth.thecat.data.repository

import androidx.paging.PagingData
import com.swordhealth.thecat.data.entities.CatEntity
import kotlinx.coroutines.flow.Flow

interface CatRepository {
    fun getCatsPaging(): Flow<PagingData<CatEntity>>
    fun searchCatsPaging(search: String): Flow<List<CatEntity>>
}