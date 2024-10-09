package com.swordhealth.thecat.usecases

import androidx.paging.PagingData
import androidx.paging.map
import com.swordhealth.thecat.data.entities.CatEntity
import com.swordhealth.thecat.data.repository.CatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetCatsUseCase(
    private val repository: CatRepository,
    private val getFavoritesCatsUseCase: GetFavoritesCatsUseCase
) : BaseUseCase<Unit, Flow<PagingData<CatEntity>>> {

    override suspend fun execute(input: Unit): Flow<PagingData<CatEntity>> {
        return combine(
            repository.getCatsPaging(),
            getFavoritesCatsUseCase.execute(Unit)
        ) { pagingData, favoritesList ->
            val favoritesMap = favoritesList.associateBy { it.image?.id }

            pagingData.map { catEntity ->
                val favoriteEntity = favoritesMap[catEntity.image?.id]

                catEntity.copy(idFavorite = favoriteEntity?.id)
            }
        }
    }
}
