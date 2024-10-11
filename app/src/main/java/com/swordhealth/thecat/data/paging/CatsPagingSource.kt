package com.swordhealth.thecat.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.swordhealth.thecat.data.api.CatsApi
import com.swordhealth.thecat.data.entities.CatEntity
import com.swordhealth.thecat.data.entities.FavoriteRequestDto
import com.swordhealth.thecat.data.localdatabase.dao.CatDao
import kotlinx.coroutines.flow.first
import java.io.IOException

class CatsPagingSource(
    private val catsApi: CatsApi,
    private val catDao: CatDao
) : PagingSource<Int, CatEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatEntity> {
        val currentPage = params.key ?: 0

        return try {
            syncLocalFavoritesWithApi()

            val apiCats = catsApi.getCats(page = currentPage)
            val apiCatIds = apiCats.map { it.id }

            catDao.deleteCatsByIds(apiCatIds)

            val favorites = catsApi.getFavorites()
            val favoritesMap = favorites.associateBy { it.image?.id }

            val updatedCats = apiCats.map { apiCat ->
                val favoriteEntity = favoritesMap[apiCat.image?.id]
                val newFavoriteId = favoriteEntity?.id

                val textInformations = favoriteEntity?.name?.split(".") ?: emptyList()

                val lifeSpanFavorite = if (textInformations.size > 1) textInformations[1] else ""

                val lifeSpanRange = lifeSpanFavorite.split("-").mapNotNull { it.trim().toIntOrNull() }

                val averageLifeSpan = if (lifeSpanRange.size == 2) {
                    (lifeSpanRange[0] + lifeSpanRange[1]) / 2
                } else {
                    0
                }

                apiCat.copy(
                    idFavorite = newFavoriteId,
                    lifeSpan = "$averageLifeSpan"
                )
            }.sortedBy { it.name }


            catDao.saveCats(updatedCats)

            LoadResult.Page(
                data = updatedCats,
                prevKey = if (currentPage == 0) null else currentPage - 1,
                nextKey = if (apiCats.isEmpty()) null else currentPage + 1
            )
        } catch (exception: IOException) {
            val localCats = catDao.getCats().first()

            LoadResult.Page(
                data = localCats,
                prevKey = null,
                nextKey = null
            )
        } catch (exception: retrofit2.HttpException) {
            LoadResult.Error(exception)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CatEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    private suspend fun syncLocalFavoritesWithApi() {
        val allCats = catDao.getCats().first()
        val pendingCats = allCats.filter { it.isPendingSync == true }

        pendingCats.forEach { cat ->
            try {
                if (cat.idFavorite != null) {

                    catsApi.setFavorite(FavoriteRequestDto(imageId = cat.image?.id ?: "", subId = "${cat.name}.${cat.lifeSpan}"))
                } else {
                    val favoriteEntity = catsApi.getFavorites().find { it.image?.id == cat.image?.id }

                    if (favoriteEntity != null) {
                        catsApi.deleteFavorite(favoriteEntity.id ?: "")
                    }
                }

                catDao.updateFavoriteStatus(cat.name, null)
            } catch (e: Exception) {}
        }
    }
}



