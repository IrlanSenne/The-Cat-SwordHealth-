package com.swordhealth.thecat.ui.screens

import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.swordhealth.thecat.CatRoutes
import com.swordhealth.thecat.MainViewModel
import com.swordhealth.thecat.R
import com.swordhealth.thecat.data.entities.CatUI
import com.swordhealth.thecat.navigateWithObject
import com.swordhealth.thecat.ui.mappers.toUI
import com.swordhealth.thecat.ui.widgets.CatGrid


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesCatsScreen(
    mainViewModel: MainViewModel? = null,
    navController: NavHostController? = null
) {
    val catsFavouritesPagingItems = mainViewModel?.favoritesCatsState?.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.favorites),
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (catsFavouritesPagingItems?.itemCount == 0) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    stringResource(R.string.favourites_empty_list),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W300,
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            } else {
                CatGrid(
                    isHomeScreen = false,
                    listLazy = catsFavouritesPagingItems,
                    onClickFavourite = { cat ->
                        mainViewModel?.deleteFavorite(cat.idFavorite ?: "")
                    },
                    onClickDetail = { catEntity ->
                        navigateWithObject(
                            navController,
                            CatRoutes.catDetailScreen,
                            catEntity.toUI(true),
                            CatUI.serializer()
                        )
                    }
                )
            }
        }
    }
}