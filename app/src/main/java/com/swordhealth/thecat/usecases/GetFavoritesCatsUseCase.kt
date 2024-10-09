package com.swordhealth.thecat.usecases

import com.swordhealth.thecat.data.entities.FavoriteEntity
import com.swordhealth.thecat.data.repository.CatRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritesCatsUseCase(
    private val repository: CatRepository
) : BaseUseCase<Unit, Flow<List<FavoriteEntity>>> {

    override suspend fun execute(input: Unit): Flow<List<FavoriteEntity>> {
        return repository.getFavorites()
    }
}