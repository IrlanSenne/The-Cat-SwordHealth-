package com.swordhealth.thecat.usecases

import android.util.Log
import androidx.paging.PagingData
import androidx.paging.map
import com.swordhealth.thecat.data.entities.CatEntity
import com.swordhealth.thecat.data.repository.CatRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class GetCatsUseCase(
    private val repository: CatRepository
) : BaseUseCase<Unit, Flow<PagingData<CatEntity>>> {

    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun execute(input: Unit): Flow<PagingData<CatEntity>> {
        return combine(
            repository.getCatsPaging(),
            repository.getFavorites()
        ) { pagingData, favoritesList ->

            val favoritesMap = favoritesList.associateBy { it.image?.id }

            repository.getCatsLocal()
                .onEach { cats ->
                    if (cats.isNotEmpty()) {
                        Log.d("testLocal", "cats saved: $cats")
                    } else {
                        Log.d("testLocal", "no cats saved")
                    }
                }
                .launchIn(GlobalScope)

            val listCarEntityUpdated = pagingData.map { catEntity ->
                val favoriteEntity = favoritesMap[catEntity.image?.id]
                val updatedCat = catEntity.copy(idFavorite = favoriteEntity?.id)

                repository.saveCatLocal(updatedCat).collect()
                updatedCat
            }

            listCarEntityUpdated
        }
    }
}



