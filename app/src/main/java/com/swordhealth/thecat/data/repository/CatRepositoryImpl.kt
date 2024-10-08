package com.swordhealth.thecat.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.swordhealth.thecat.data.api.CatsApi
import com.swordhealth.thecat.data.entities.CatEntity
import com.swordhealth.thecat.data.paging.CatsPagingSource
import kotlinx.coroutines.flow.Flow

class CatRepositoryImpl(private val catsApi: CatsApi) : CatRepository {

    override fun getCatsPaging(): Flow<PagingData<CatEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 18,
                enablePlaceholders = false,
                prefetchDistance = 2
            ),
            pagingSourceFactory = { CatsPagingSource(catsApi) }
        ).flow
    }
}


