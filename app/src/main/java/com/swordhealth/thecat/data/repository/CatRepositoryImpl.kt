package com.swordhealth.thecat.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.swordhealth.thecat.data.api.CatsApi
import com.swordhealth.thecat.data.entities.CatEntity
import com.swordhealth.thecat.data.entities.FavoriteEntity
import com.swordhealth.thecat.data.entities.FavoriteRequestDto
import com.swordhealth.thecat.data.entities.ImageEntity
import com.swordhealth.thecat.data.localdatabase.dao.CatDao
import com.swordhealth.thecat.data.paging.CatsPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class CatRepositoryImpl(
    private val catsApi: CatsApi,
    private val catDao: CatDao,
) : CatRepository {

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

    override fun setAsFavorite(imageId: String, subId: String?): Flow<ImageEntity?> {
        return flow {
            try {
                val result = catsApi.setFavorite(FavoriteRequestDto(imageId, subId))
                emit(ImageEntity(id = result.id, url = result.url))
            } catch (e: Exception) {
                emit(null)
            }
        }
    }

    override fun deleteFromFavorite(id: String): Flow<ImageEntity?> {
        return flow {
            try {
                val result = catsApi.deleteFavorite(id)
                emit(ImageEntity(id = result.id, url = result.url))
            } catch (e: Exception) {
                emit(null)
            }
        }
    }

    override fun getCatsLocal(): Flow<List<CatEntity>> {
        return catDao.getCats()
    }

    override suspend fun saveCatLocal(cat: CatEntity): Flow<Unit> {
        return flow {
            try {
                catDao.saveCats(cat)
                emit(Unit)
            } catch (e: Exception) {
                emit(Unit)
            }
        }
    }
}

