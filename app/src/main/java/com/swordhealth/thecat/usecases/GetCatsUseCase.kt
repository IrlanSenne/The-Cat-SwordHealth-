package com.swordhealth.thecat.usecases

import androidx.paging.PagingData
import com.swordhealth.thecat.data.entities.CatEntity
import com.swordhealth.thecat.data.repository.CatRepository
import kotlinx.coroutines.flow.Flow

class GetCatsUseCase(
    private val repository: CatRepository
) : BaseUseCase<Unit, Flow<PagingData<CatEntity>>> {

    override suspend fun execute(input: Unit): Flow<PagingData<CatEntity>> {
        return repository.getCatsPaging()
    }
}
