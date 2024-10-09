package com.swordhealth.thecat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.swordhealth.thecat.data.entities.CatEntity
import com.swordhealth.thecat.usecases.GetCatsUseCase
import com.swordhealth.thecat.usecases.SearchCatsUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val getCatsUseCase: GetCatsUseCase,
    private val searchCatsUseCase: SearchCatsUseCase
) : ViewModel() {
    private val _catsState: MutableStateFlow<PagingData<CatEntity>> = MutableStateFlow(PagingData.empty())
    val catsState: StateFlow<PagingData<CatEntity>> get() = _catsState

    private var searchJob: Job? = null
    private val searchQuery = MutableStateFlow("")

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

    fun searchCats(query: String) {
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(300)

            if (query.isNotEmpty()) {
                searchCatsUseCase.execute(query)
                    .collect { listCatEntity ->
                        Log.d("searchCats", "$listCatEntity")
                        val pagingData = PagingData.from(listCatEntity)
                        _catsState.value = pagingData
                    }
            } else {
                fetchCats()
            }
        }
    }

    fun updateSearchQuery(query: String) {
        searchQuery.value = query
        searchCats(query)
    }
}





