package com.swordhealth.thecat.usecases

import com.swordhealth.thecat.data.entities.ImageEntity
import com.swordhealth.thecat.data.repository.CatRepository
import kotlinx.coroutines.flow.Flow

class DeleteFavouriteUseCase(
    private val repository: CatRepository
) : BaseUseCase<String, Flow<ImageEntity?>> {

    override suspend fun execute(id: String) : Flow<ImageEntity?> {
        return repository.deleteFromFavorite(id = id)
    }
}