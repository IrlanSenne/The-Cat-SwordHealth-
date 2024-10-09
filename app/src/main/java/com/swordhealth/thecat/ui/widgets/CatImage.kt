package com.swordhealth.thecat.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.swordhealth.thecat.R
import com.swordhealth.thecat.data.entities.CatEntity

@Composable
fun CatImage(cat: CatEntity) {
    val imageUrl = cat.image?.url ?: ""
    val width = cat.image?.width ?: 1
    val height = cat.image?.height ?: 1

    val aspectRatio = width.toFloat() / height.toFloat()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = imageUrl,
                error = painterResource(id = R.drawable.bg_the_cat_place_holder),
            ),
            contentDescription = "Cat Image",
            contentScale = if (aspectRatio > 1) {
                ContentScale.Crop
            } else {
                ContentScale.FillBounds
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
