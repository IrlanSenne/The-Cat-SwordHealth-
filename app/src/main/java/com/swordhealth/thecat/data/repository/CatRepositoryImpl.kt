package com.swordhealth.thecat.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.swordhealth.thecat.data.api.CatsApi
import com.swordhealth.thecat.data.entities.CatEntity
import com.swordhealth.thecat.data.paging.CatsPagingSource

class CatRepositoryImpl(private val catsApi: CatsApi) : CatRepository {

    override fun getCatsPaging(): Pager<Int, CatEntity> {
        return Pager(
            config = PagingConfig(
                pageSize = 18,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CatsPagingSource(catsApi) }
        )
    }
}


