package com.swordhealth.thecat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.map
import com.swordhealth.thecat.data.entities.CatEntity
import com.swordhealth.thecat.data.repository.CatRepository
import com.swordhealth.thecat.usecases.GetCatsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val getCatsUseCase: GetCatsUseCase) : ViewModel() {
    private val _catsState: MutableStateFlow<PagingData<CatEntity>> = MutableStateFlow(PagingData.empty())
    val catsState: StateFlow<PagingData<CatEntity>> get() = _catsState

    init {
        fetchCats()
    }

    private fun fetchCats() {
        viewModelScope.launch {
            getCatsUseCase.execute(Unit)
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    Log.d("fetchCats", "$pagingData")
                    _catsState.value = pagingData
                }
        }
    }
}

