package com.swordhealth.thecat.ui.widgets


import android.graphics.ColorFilter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.swordhealth.thecat.R

@Composable
fun FavoriteIcon(
    sizeBox: Int,
    sizeImage: Int,
    isFavorite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val imageId = if (isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favourite_empty
    val color = if (isFavorite) Color.Red else Color.White

    Box(
        modifier = modifier
            .size(sizeBox.dp)
            .clip(CircleShape)
            .background(Color.Gray.copy(alpha = 0.5f))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(sizeImage.dp),
            painter = painterResource(id = imageId),
            contentDescription = "Favorite Icon",
            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(color)
        )
    }
}
