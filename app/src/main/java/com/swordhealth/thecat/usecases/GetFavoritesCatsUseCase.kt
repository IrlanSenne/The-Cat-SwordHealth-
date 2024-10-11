package com.swordhealth.thecat.usecases

import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import com.swordhealth.thecat.data.entities.CatEntity
import com.swordhealth.thecat.data.repository.CatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetFavoritesCatsUseCase(
    private val repository: CatRepository
) : BaseUseCase<Unit, Flow<PagingData<CatEntity>>> {

    override suspend fun execute(input: Unit): Flow<PagingData<CatEntity>> {
        return flow {
            try {
                val pagingData = repository.getCatsPaging().firstOrNull()

                if (pagingData != null) {
                    emit(pagingData.filter { it.idFavorite != null })
                } else {
                    emit(getLocalFavorites())
                }
            } catch (e: Exception) {
                emit(getLocalFavorites())
            }
        }
    }

    private suspend fun getLocalFavorites(): PagingData<CatEntity> {
        val localCats = repository.getCatsLocal().first()
        val favoriteCats = localCats.filter { !it.idFavorite.isNullOrEmpty() }

        return PagingData.from(favoriteCats)
    }
}