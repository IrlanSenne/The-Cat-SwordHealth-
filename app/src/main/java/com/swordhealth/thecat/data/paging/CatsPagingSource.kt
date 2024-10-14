package com.swordhealth.thecat.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.swordhealth.thecat.data.api.CatsApi
import com.swordhealth.thecat.data.entities.CatEntity
import com.swordhealth.thecat.data.entities.FavoriteEntity
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

                apiCat.copy(
                    idFavorite = newFavoriteId,
                    isPendingSync = false
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

    private fun fl(favoriteEntity: FavoriteEntity?): Float {
        val textInformations = favoriteEntity?.subId?.split(".") ?: emptyList()

        // Verificar se existe um segundo elemento (a parte de vida útil)
        val lifeSpanFavorite = if (textInformations.size > 1) textInformations[1].trim() else ""

        // Debug: Verificar o valor extraído
        println("lifeSpanFavorite: '$lifeSpanFavorite'")

        // Tentar fazer o split usando "-" e pegar o primeiro número
        val firstLifeSpan = lifeSpanFavorite.split("-").firstOrNull()?.trim()?.toFloatOrNull() ?: 0f

        // Debug: Verificar o primeiro número da faixa de vida útil
        println("First Life Span: $firstLifeSpan")
        return firstLifeSpan
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
                if (cat.idFavorite?.startsWith("TEMP_") == true) {
                    val response = catsApi.setFavorite(
                        FavoriteRequestDto(
                            imageId = cat.image?.id ?: "",
                            subId = "${cat.name}.${cat.lifeSpan}"
                        )
                    )

                    val newFavoriteId = response.id

                    catDao.updateFavoriteStatus(
                        name = cat.name,
                        idFavorite = newFavoriteId.toString(),
                        isPendingSync = false
                    )

                } else if (cat.idFavorite != null) {
                    cat.idFavorite?.let { catsApi.deleteFavorite(it) }

                    catDao.updateFavoriteStatus(
                        name = cat.name,
                        idFavorite = null,
                        isPendingSync = false
                    )
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}



