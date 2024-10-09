package com.swordhealth.thecat.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.swordhealth.thecat.MainViewModel
import com.swordhealth.thecat.R
import com.swordhealth.thecat.ui.theme.TheCatTheme
import com.swordhealth.thecat.ui.widgets.CatGrid
import com.swordhealth.thecat.ui.widgets.CatTextFielad
import org.koin.core.definition.indexKey

@Composable
fun CatListScreen(
    mainViewModel: MainViewModel? = null,
    isLoadingValueChange: (Boolean) -> Unit
) {
    val catsPagingItems = mainViewModel?.catsState?.collectAsLazyPagingItems()
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        CatTextFielad(searchQuery) {
            searchQuery = it
            mainViewModel?.updateSearchQuery(searchQuery)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (catsPagingItems != null) {
            CatGrid(
                listLazy = catsPagingItems,
                onClickFavourite = { cat ->
                    if (!cat.idFavorite.isNullOrEmpty()) {
                        mainViewModel.deleteFavorite(cat.idFavorite ?: "")
                    } else {
                        if (cat.image?.id?.isNotEmpty() == true) mainViewModel.setAsFavorite(
                            cat.image.id,
                            "${cat.name}.${cat.lifeSpan}"
                        )
                    }

                    Log.d("testClick", "onClickFavourite")
                },
                onClickDetail = { cat ->
                    Log.d("testClick", "onClickDetail")
                    catsPagingItems.apply {
                        when {
                            loadState.append is LoadState.Loading -> isLoadingValueChange(true)
                            else -> isLoadingValueChange(false)
                        }
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CatListScreenPreview() {
    TheCatTheme {
        CatListScreen {}
    }
}
