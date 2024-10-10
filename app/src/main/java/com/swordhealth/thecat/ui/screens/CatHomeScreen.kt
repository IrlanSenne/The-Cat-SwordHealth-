package com.swordhealth.thecat.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.swordhealth.thecat.MainViewModel
import com.swordhealth.thecat.R
import com.swordhealth.thecat.ui.theme.TheCatTheme
import com.swordhealth.thecat.ui.widgets.CatsBarItem
import com.swordhealth.thecat.ui.widgets.CatsBottomBar
import com.swordhealth.thecat.ui.widgets.CatsLoading

@Composable
fun CatHomeScreen(
    mainViewModel: MainViewModel? = null,
    navController: NavHostController? = null,
    isFromFavourites: Boolean = false
) {
    var isLoading by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(if (isFromFavourites) 1 else 0) }

    val tabsNavigation = listOf(
        CatsBarItem(stringResource(id = R.string.cats), Icons.Default.Home),
        CatsBarItem(stringResource(id = R.string.favorites), Icons.Default.Favorite)
    )

    TheCatTheme {
        Scaffold(
            bottomBar = {
                CatsBottomBar(
                    list = tabsNavigation,
                    selectedIndex = selectedIndex
                ) {
                    selectedIndex = it
                }
            }) { innerPadding ->

            when (selectedIndex) {
                0 -> CatListScreen(mainViewModel, navController) { isLoading = it }
                1 -> FavoritesCatsScreen(mainViewModel, navController)
            }

            CatsLoading(isLoading)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CatHomePreview() {
    val tabsNavigation = listOf(
        CatsBarItem("Cats", Icons.Default.Home),
        CatsBarItem("Favorites", Icons.Default.Favorite)
    )

    TheCatTheme {
        Scaffold(
            bottomBar = {
                CatsBottomBar(
                    list = tabsNavigation,
                    selectedIndex = 0
                ) {}

            }) { it ->
            CatListScreen() {}
        }
    }
}