package com.swordhealth.thecat.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.swordhealth.thecat.data.api.CatsApi
import com.swordhealth.thecat.data.entities.CatEntity
import com.swordhealth.thecat.data.entities.FavoriteEntity
import com.swordhealth.thecat.data.entities.FavoriteRequestDto
import com.swordhealth.thecat.data.entities.SetAsFavouriteResponse
import com.swordhealth.thecat.data.localdatabase.dao.CatDao
import com.swordhealth.thecat.data.paging.CatsPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CatRepositoryImpl(
    private val catsApi: CatsApi,
    private val catDao: CatDao,
) : CatRepository {

    override fun getCatsPaging(): Flow<PagingData<CatEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 18,
                enablePlaceholders = false,
                prefetchDistance = 1
            ),
            pagingSourceFactory = {
                CatsPagingSource(catsApi, catDao)
            }
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
            val result = try {
                catsApi.getFavorites()
            } catch (e: Exception) {
                emit(emptyList())
                return@flow
            }
            emit(result)
        }
    }

    override fun setAsFavorite(imageId: String, subId: String?): Flow<SetAsFavouriteResponse?> {
        return flow {
            try {
                val result = catsApi.setFavorite(FavoriteRequestDto(imageId, subId))
                emit(SetAsFavouriteResponse(id = result.id))
            } catch (e: Exception) {
                emit(null)
            }
        }
    }

    override fun updateCatFavorite(name: String, idFavourite: String?, isPendingSync: Boolean): Flow<Unit> {
        return flow {
            try {
                catDao.updateFavoriteStatus(name, idFavourite, isPendingSync)
                emit(Unit)
            } catch (e: Exception) {
                emit(Unit)
            }
        }
    }

    override fun deleteFromFavorite(id: String): Flow<SetAsFavouriteResponse?> {
        return flow {
            try {
                val result = catsApi.deleteFavorite(id)
                emit(SetAsFavouriteResponse(id = result.id))
            } catch (e: Exception) {
                emit(null)
            }
        }
    }

    override fun getCatsLocal(): Flow<List<CatEntity>> {
        return catDao.getCats()
    }

    override suspend fun saveCatLocal(cats: List<CatEntity>): Flow<Unit> {
        return flow {
            try {
                catDao.saveCats(cats)
                emit(Unit)
            } catch (e: Exception) {
                emit(Unit)
            }
        }
    }
}

