package com.swordhealth.thecat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import com.swordhealth.thecat.data.entities.CatEntity
import com.swordhealth.thecat.data.repository.CatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    catRepository: CatRepository
) : ViewModel() {

    val catsPagingFlow = catRepository.getCatsPaging()
        .flow
        .cachedIn(viewModelScope)

    init {
        fetchCats()
    }

    private fun fetchCats() {
        viewModelScope.launch {
            try {
               // Log.d("fetchCats", "Cats fetched: ${catsPagingFlow.}")
            } catch (e: Exception) {
                Log.e("fetchCats", "Error fetching cats: ${e.message}")
            }
        }
    }
}
