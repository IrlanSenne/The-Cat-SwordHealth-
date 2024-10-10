package com.swordhealth.thecat.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import com.swordhealth.thecat.MainViewModel
import com.swordhealth.thecat.ui.widgets.CatGrid


@Composable
fun FavoritesCatsScreen(
    mainViewModel: MainViewModel? = null,
    navController: NavHostController? = null
) {
    val favoritesCatsList = mainViewModel?.favoritesCatsState?.collectAsState()?.value

    CatGrid(
        list = favoritesCatsList,
        onClickFavourite = { cat ->
            mainViewModel?.deleteFavorite(cat.idFavorite ?: "")
        }
    )
}