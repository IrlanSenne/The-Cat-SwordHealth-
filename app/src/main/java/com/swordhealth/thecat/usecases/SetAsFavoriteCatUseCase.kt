package com.swordhealth.thecat.usecases

import com.swordhealth.thecat.data.entities.FavoriteRequestDto
import com.swordhealth.thecat.data.entities.SetAsFavouriteResponse
import com.swordhealth.thecat.data.repository.CatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last

class SetAsFavoriteCatUseCase(
    private val repository: CatRepository
) : BaseUseCase<FavoriteRequestDto, Flow<SetAsFavouriteResponse?>> {

    override suspend fun execute(
        favoriteRequestDto: FavoriteRequestDto
    ): Flow<SetAsFavouriteResponse?> {
        return flow {

            val favouriteResponse = repository.setAsFavorite(
                imageId = favoriteRequestDto.imageId,
                subId = favoriteRequestDto.subId
            ).last()

            if (favouriteResponse != null) {
                emit(favouriteResponse)
            } else {
                updateLocalFavorite(favoriteRequestDto.imageId)
                emit(null)
            }
        }.catch { e ->
            emit(null)
        }
    }

    private suspend fun updateLocalFavorite(
        imageId: String?
    ) {
        val localCats = repository.getCatsLocal().first().sortedBy { it.name }.toMutableList()

        val catToUpdate = localCats.find { it.image?.id == imageId }

        if (catToUpdate != null) {
            val updatedCat = catToUpdate.copy(idFavorite = null, isPendingSync = true)

            localCats[localCats.indexOf(catToUpdate)] = updatedCat

            repository.saveCatLocal(localCats).collect {}
        }
    }

}




