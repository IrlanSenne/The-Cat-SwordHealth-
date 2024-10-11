package com.swordhealth.thecat.usecases

import android.util.Log
import com.swordhealth.thecat.data.entities.CatEntity
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
                localCat?.let {
                    updateLocalFavorite(it, isPendingSync = false)
                }
                emit(deleteResponse)
            } else {
                localCat?.let {
                    updateLocalFavorite(it, isPendingSync = true)
                }
                emit(null)
            }
        }.catch { e ->
            emit(null)
        }
    }

    private suspend fun updateLocalFavorite(cat: CatEntity, isPendingSync: Boolean) {
        val updatedCat = cat.copy(idFavorite = null, isPendingSync = isPendingSync)
        repository.saveCatLocal(listOf(updatedCat)).collect {}
    }
}