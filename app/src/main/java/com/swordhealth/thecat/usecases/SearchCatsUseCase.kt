package com.swordhealth.thecat.usecases

import com.swordhealth.thecat.data.entities.CatEntity
import com.swordhealth.thecat.data.repository.CatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow

class SearchCatsUseCase(
    private val repository: CatRepository
) : BaseUseCase<String, Flow<List<CatEntity>>> {

    override suspend fun execute(input: String): Flow<List<CatEntity>> {
        return flow {
            try {
                val apiCats = repository.searchCatsPaging(input).firstOrNull()

                if (!apiCats.isNullOrEmpty()) {
                    val favoritesList = repository.getFavorites().first()
                    val favoritesMap = favoritesList.associateBy { it.image?.id }

                    val updatedCats = apiCats.map { catEntity ->
                        val favoriteEntity = favoritesMap[catEntity.image?.id]
                        catEntity.copy(idFavorite = favoriteEntity?.id)
                    }

                    emit(updatedCats)
                } else {
                    emit(searchLocalCats(input))
                }
            } catch (e: Exception) {
                emit(searchLocalCats(input))
            }
        }
    }

    private suspend fun searchLocalCats(query: String): List<CatEntity> {
        val localCats = repository.getCatsLocal().first()

        val filteredLocalCats = localCats.filter {
            it.name.contains(query, ignoreCase = true)
        }

        val favoritesList = repository.getFavorites().first()
        val favoritesMap = favoritesList.associateBy { it.image?.id }

        return filteredLocalCats.map { catEntity ->
            val favoriteEntity = favoritesMap[catEntity.image?.id]
            catEntity.copy(idFavorite = favoriteEntity?.id)
        }
    }
}
