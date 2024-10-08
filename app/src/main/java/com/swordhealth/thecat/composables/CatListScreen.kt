package com.swordhealth.thecat.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.LoadState
import androidx.paging.compose.itemKey
import com.swordhealth.thecat.MainViewModel

@Composable
fun CatListScreen(viewModel: MainViewModel) {
    val catsPagingItems = viewModel.catsPagingFlow.collectAsLazyPagingItems()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(catsPagingItems.itemSnapshotList) { index, cat ->
            cat?.let {
                AsyncImage(
                    model = cat.url,
                    contentDescription = null,
                    modifier = Modifier
                        .size(128.dp)
                        .padding(8.dp)
                )
            }
        }

        catsPagingItems.apply {
            when {
                loadState.append is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator(modifier = Modifier.fillMaxWidth())
                    }
                }
                loadState.append is LoadState.Error -> {
                    item {
                        Text("Error loading more cats!")
                    }
                }
            }
        }
    }
}