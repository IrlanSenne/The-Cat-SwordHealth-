package com.swordhealth.thecat.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.swordhealth.thecat.MainViewModel
import com.swordhealth.thecat.R
import com.swordhealth.thecat.ui.widgets.CatGrid


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesCatsScreen(
    mainViewModel: MainViewModel? = null,
    navController: NavHostController? = null
) {
    val favoritesCatsList = mainViewModel?.favoritesCatsState?.collectAsState()?.value

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
        CatGrid(
            modifier = Modifier.padding(paddingValues),
            list = favoritesCatsList,
            onClickFavourite = { cat ->
                mainViewModel?.deleteFavorite(cat.idFavorite ?: "")
            }
        )
    }
}