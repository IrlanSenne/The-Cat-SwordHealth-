package com.swordhealth.thecat.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.swordhealth.thecat.MainViewModel


@Composable
fun FavoritesCatsScreen(
    mainViewModel: MainViewModel
) {
    val favoritesCatsList = mainViewModel.favoritesCatsState.collectAsState().value

    LaunchedEffect(favoritesCatsList) {
        mainViewModel.getFavoritesCats()
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(favoritesCatsList) { _, favoriteCat ->

            AsyncImage(
                model = favoriteCat.image?.url,
                contentDescription = null,
                modifier = Modifier
                    .size(128.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}