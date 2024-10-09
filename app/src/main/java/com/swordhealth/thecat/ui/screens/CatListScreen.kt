package com.swordhealth.thecat.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.swordhealth.thecat.MainViewModel
import com.swordhealth.thecat.R

@Composable
fun CatListScreen(
    mainViewModel: MainViewModel,
    isLoadingValueChange: (Boolean) -> Unit
) {
    val catsPagingItems = mainViewModel.catsState.collectAsLazyPagingItems()
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = searchQuery,
            onValueChange = { query ->
                searchQuery = query
                mainViewModel.updateSearchQuery(searchQuery)
            },
            placeholder = { Text(stringResource(id = R.string.search_by_breed)) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(catsPagingItems.itemCount) { index ->

                val cat = catsPagingItems[index]
                Log.d("searchCats", "${cat?.idFavorite}")
                Row {
                    cat?.let {
                        Box (
                            modifier = Modifier
                                .size(128.dp)
                                .padding(8.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable(enabled = true) {

                                    if (!cat.idFavorite.isNullOrEmpty()) {
                                        mainViewModel.deleteFavorite(cat.idFavorite ?: "")
                                    } else {
                                        if (it.image?.id?.isNotEmpty() == true) mainViewModel.setAsFavorite(it.image.id, it.name)
                                    }
                                }
                        ) {
                            AsyncImage(
                                modifier = Modifier.fillMaxSize(),
                                model = it.image?.url,
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    Text("${cat?.name}")
                }
            }

            item {
                Spacer(modifier = Modifier.height(120.dp))
            }

            catsPagingItems.apply {
                when {
                    loadState.append is LoadState.Loading -> {
                        item {
                            isLoadingValueChange(true)
                        }
                    }

                    else -> {
                        isLoadingValueChange(false)
                    }
                }
            }
        }
    }
}
