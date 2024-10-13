package com.swordhealth.thecat.usecases

import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import com.swordhealth.thecat.data.entities.CatEntity
import com.swordhealth.thecat.data.repository.CatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetFavoritesCatsUseCase(
    private val repository: CatRepository
) : BaseUseCase<Unit, Flow<PagingData<CatEntity>>> {

    override suspend fun execute(input: Unit): Flow<PagingData<CatEntity>> {
        return flow {
            try {
                val pagingData = repository.getCatsPaging().firstOrNull()


                pagingData?.filter {
                    (it.idFavorite?.isNotBlank() == true && !it.idFavorite!!.startsWith("TEMP_") && !it.isPendingSync) ||
                            (it.isPendingSync && it.idFavorite?.startsWith("TEMP_") == true)
                }?.let { emit(it) }


            } catch (e: Exception) {
            }
        }
    }
}