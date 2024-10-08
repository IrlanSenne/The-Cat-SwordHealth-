package com.swordhealth.thecat.composables.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.swordhealth.thecat.MainViewModel

@Composable
fun FavoritesCatsScreen(
    mainViewModel: MainViewModel,
    isLoadingValueChange: (Boolean) -> Unit
) {
    val catsPagingItems = mainViewModel.catsState.collectAsLazyPagingItems()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(catsPagingItems.itemCount) { index ->
            val cat = catsPagingItems[index]
            cat?.let {
                AsyncImage(
                    model = cat.image?.url,
                    contentDescription = null,
                    modifier = Modifier
                        .size(128.dp)
                        .padding(8.dp)
                )
            }
        }

        catsPagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        isLoadingValueChange(true)
                    }
                }

                loadState.refresh is LoadState.Error -> {
                    val error = loadState.refresh as LoadState.Error
                    item {
                        //TODO: Error
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item {
                        isLoadingValueChange(true)
                    }
                }

                loadState.append is LoadState.Error -> {
                    val error = loadState.append as LoadState.Error
                    item {
                        //TODO: Error
                    }
                }

                else -> {
                    isLoadingValueChange(false)
                }
            }
        }
    }
}