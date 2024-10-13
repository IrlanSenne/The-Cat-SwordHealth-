package com.swordhealth.thecat.usecases

import com.swordhealth.thecat.data.entities.SetAsFavouriteResponse
import com.swordhealth.thecat.data.repository.CatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last

class DeleteFavouriteUseCase(
    private val repository: CatRepository
) : BaseUseCase<String, Flow<SetAsFavouriteResponse?>> {

    override suspend fun execute(id: String): Flow<SetAsFavouriteResponse?> {
        return flow {

            val localCat = repository.getCatsLocal().first().find { it.idFavorite == id }

            val deleteResponse = try {
                repository.deleteFromFavorite(id = id).last()
            } catch (e: Exception) {
                null
            }

            if (deleteResponse != null) {
                emit(deleteResponse)
            } else {
                localCat?.let { deleteLocalFavorite(it.image?.id, id) }
                emit(null)
            }
        }.catch { e ->
            emit(null)
        }
    }

    private suspend fun deleteLocalFavorite(imageId: String?, id: String?) {
        val localCats = repository.getCatsLocal().first().toMutableList()
        val catToUpdate = localCats.find { it.image?.id == imageId }

        if (catToUpdate != null) {
            val isTemporaryFavorite = id?.startsWith("TEMP_") == true

            val updatedCat = if (isTemporaryFavorite) {
                catToUpdate.copy(isPendingSync = false, idFavorite = null)
            } else {
                catToUpdate.copy(isPendingSync = true)
            }

            localCats[localCats.indexOf(catToUpdate)] = updatedCat

            repository.saveCatLocal(localCats).collect {}
        }
    }
}