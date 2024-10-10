package com.swordhealth.thecat.usecases

import com.swordhealth.thecat.data.entities.CatEntity
import com.swordhealth.thecat.data.repository.CatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class SearchCatsUseCase(
    private val repository: CatRepository
) : BaseUseCase<String, Flow<List<CatEntity>>> {

    override suspend fun execute(input: String): Flow<List<CatEntity>> {
        return combine(
            repository.searchCatsPaging(input),
            repository.getFavorites()
        ) { catList, favoritesList ->
            val favoritesMap = favoritesList.associateBy { it.image?.id }

            catList.map { catEntity ->
                val favoriteEntity = favoritesMap[catEntity.image?.id]

                catEntity.copy(idFavorite = favoriteEntity?.id)
            }
        }
    }
}
