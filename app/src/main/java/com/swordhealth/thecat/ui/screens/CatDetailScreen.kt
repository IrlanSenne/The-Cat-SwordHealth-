package com.swordhealth.thecat.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.swordhealth.thecat.MainViewModel
import com.swordhealth.thecat.R
import com.swordhealth.thecat.data.entities.CatUI
import com.swordhealth.thecat.ui.widgets.FavoriteIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatDetailScreen(
    mainViewModel: MainViewModel,
    navController: NavHostController? = null,
    catUI: CatUI?
) {
    var isFavorite by remember { mutableStateOf(catUI?.idFavorite?.isNotEmpty() == true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.details),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController?.navigateUp() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "-")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            catUI?.image?.url?.let { imageUrl ->
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = "${catUI.name}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = catUI?.name ?: "-",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            RowItemIformation(stringResource(R.string.origin), catUI?.origin ?: "")
            RowItemIformation(stringResource(R.string.temperament), catUI?.temperament ?: "")
            RowItemIformation(stringResource(R.string.description), catUI?.description ?: "")

            Spacer(modifier = Modifier.height(20.dp))

            FavoriteIcon(
                sizeBox = 96,
                sizeImage = 84,
                isFavorite = isFavorite,
                onClick = {
                    val idFavorite = catUI?.idFavorite
                    val imageId = catUI?.image?.id

                    if (!idFavorite.isNullOrEmpty()) {
                        mainViewModel.deleteFavorite(idFavorite, true)
                        isFavorite = !isFavorite
                        return@FavoriteIcon
                    }

                    if (!imageId.isNullOrEmpty()) {
                        mainViewModel.setAsFavorite(imageId, "${catUI.name}.${catUI.lifeSpan}", true)
                        isFavorite = !isFavorite
                    }
                }
            )


            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(if (!isFavorite) R.string.add_to_favourites else R.string.remove_from_favourites),
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}


@Composable
fun RowItemIformation(attribute: String, value: String) {

    Spacer(modifier = Modifier.height(12.dp))
    Text(
        modifier = Modifier.padding(bottom = 4.dp),
        text = attribute,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = Color.Gray
    )

    Text(
        text = value,
        fontSize = 14.sp,
        textAlign = TextAlign.Center,
        color = Color.Gray
    )
}
