package com.swordhealth.thecat.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.swordhealth.thecat.data.api.CatsApi
import com.swordhealth.thecat.data.entities.CatEntity
import com.swordhealth.thecat.data.entities.FavoriteEntity
import com.swordhealth.thecat.data.entities.FavoriteRequestDto
import com.swordhealth.thecat.data.entities.ImageEntity
import com.swordhealth.thecat.data.paging.CatsPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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

    override fun searchCatsPaging(search: String): Flow<List<CatEntity>> {
        return flow {
            try {
                val result = catsApi.searchCats(search)
                emit(result)
            } catch (e: Exception) {
                emit(emptyList())
            }
        }
    }

    override fun getFavorites(): Flow<List<FavoriteEntity>> {
        return flow {
            try {
                val result = catsApi.getFavorites()
                emit(result)
            } catch (e: Exception) {
                emit(emptyList())
            }
        }
    }

    override fun setAsFavorite(imageId: String): Flow<ImageEntity?> {
        return flow {
            try {
                val result = catsApi.setFavorite(FavoriteRequestDto(imageId))
                emit(ImageEntity(id = result.id, url = result.url))
            } catch (e: Exception) {
                emit(null)
            }
        }
    }
}

