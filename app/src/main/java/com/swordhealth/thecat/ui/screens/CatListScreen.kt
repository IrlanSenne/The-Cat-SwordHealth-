package com.swordhealth.thecat.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.swordhealth.thecat.CatRoutes
import com.swordhealth.thecat.MainViewModel
import com.swordhealth.thecat.R
import com.swordhealth.thecat.data.entities.CatUI
import com.swordhealth.thecat.navigateWithObject
import com.swordhealth.thecat.ui.mappers.toUI
import com.swordhealth.thecat.ui.theme.TheCatTheme
import com.swordhealth.thecat.ui.widgets.CatGrid
import com.swordhealth.thecat.ui.widgets.CatTextFielad

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatListScreen(
    mainViewModel: MainViewModel? = null,
    navController: NavHostController? = null,
    isLoadingValueChange: (Boolean) -> Unit
) {
    val catsPagingItems = mainViewModel?.catsState?.collectAsLazyPagingItems()
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    CatTextFielad(searchQuery) {
                        searchQuery = it
                        mainViewModel?.updateSearchQuery(searchQuery)
                    }
                }
            )
        }
    )
    { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (catsPagingItems?.itemCount == 0) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    stringResource(R.string.no_results_search),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            } else {
                CatGrid(
                    listLazy = catsPagingItems,
                    onClickFavourite = { cat ->
                        val idFavorite = cat.idFavorite
                        val imageId = cat.image?.id

                        if (!idFavorite.isNullOrEmpty()) {
                            mainViewModel?.deleteFavorite(idFavorite)
                            return@CatGrid
                        }

                        if (!imageId.isNullOrEmpty()) {
                            mainViewModel?.setAsFavorite(imageId, "${cat.name}.${cat.lifeSpan}")
                        }
                    },
                    onClickDetail = { catEntity ->
                        navigateWithObject(
                            navController,
                            CatRoutes.catDetailScreen,
                            catEntity.toUI(),
                            CatUI.serializer()
                        )
                    }
                )
            }
        }

        catsPagingItems?.apply {
            when {
                loadState.append is LoadState.Loading -> isLoadingValueChange(true)
                loadState.refresh is LoadState.Loading -> mainViewModel.fetchCats()
                else -> isLoadingValueChange(false)
            }
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
