package com.swordhealth.thecat.usecases

import android.util.Log
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
            val textInformations = favoriteRequestDto.subId?.split(".") ?: emptyList()
            val name = if (textInformations.size > 1) textInformations[0] else ""

            val favouriteResponse = repository.setAsFavorite(
                imageId = favoriteRequestDto.imageId,
                subId = favoriteRequestDto.subId
            ).last()

            if (favouriteResponse != null) {
                Log.d("SetAsFavoriteCatUseCase", "favouriteResponse != null")
                updateLocalFavorite(name, favoriteRequestDto.imageId, false)
                emit(favouriteResponse)
            } else {
                Log.d("SetAsFavoriteCatUseCase", "favouriteResponse == NULL")
                updateLocalFavorite(name, favoriteRequestDto.imageId, true)
                emit(null)
            }
        }.catch { e ->
            emit(null)
        }
    }

    private suspend fun updateLocalFavorite(name: String, imageId: String, isPendentSync: Boolean) {
        val localCats = repository.getCatsLocal().first().toMutableList()

        val catToUpdate = localCats.find { it.name == name }
        if (catToUpdate != null) {
            val updatedCat = catToUpdate.copy(idFavorite = imageId, isPendingSync = isPendentSync)
            localCats[localCats.indexOf(catToUpdate)] = updatedCat

            repository.saveCatLocal(localCats).collect {}
        }
    }
}




