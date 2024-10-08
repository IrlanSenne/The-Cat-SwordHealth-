package com.swordhealth.thecat.usecases

import com.swordhealth.thecat.data.entities.CatEntity
import com.swordhealth.thecat.data.repository.CatRepository
import kotlinx.coroutines.flow.Flow

class SearchCatsUseCase(
    private val repository: CatRepository
) : BaseUseCase<String, Flow<List<CatEntity>>> {

    override suspend fun execute(input: String): Flow<List<CatEntity>> {
        return repository.searchCatsPaging(input)
    }
}