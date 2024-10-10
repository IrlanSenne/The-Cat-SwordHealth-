package com.swordhealth.thecat.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swordhealth.thecat.R
import com.swordhealth.thecat.data.entities.CatEntity
import com.swordhealth.thecat.data.entities.ImageEntity

@Composable
fun CatGridItem(
    cat: CatEntity,
    isHomeScreen: Boolean = false,
    onClickDetail: (CatEntity) -> Unit = {},
    onClickFavorite: (CatEntity) -> Unit = {}
) {

    val imageFavourite = when (cat.idFavorite?.isNotEmpty() == true) {
        true -> R.drawable.ic_favorite_filled
        else -> R.drawable.ic_favourite_empty
    }

    val colorImageFavourite = when (cat.idFavorite?.isNotEmpty() == true) {
        true -> Color.Red
        else -> Color.White
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(width = 136.dp, height = 116.dp)
                .clip(RoundedCornerShape(12.dp))
                .padding(4.dp)
                .clickable { onClickDetail(cat) },
            contentAlignment = Alignment.Center
        ) {
            CatImage(cat)

            FavoriteIcon(
                sizeBox = 30,
                sizeImage = 24,
                isFavorite = cat.idFavorite?.isNotEmpty() == true,
                onClick = { onClickFavorite(cat) },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = -(8.dp), y = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = cat.name ?: "",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        if (cat.idFavorite?.isNotEmpty() == true && !isHomeScreen) {
            Text(
                text = "${stringResource(R.string.average_lifespan)}: ${cat.lifeSpan ?: ""}y",
                textAlign = TextAlign.Center,
                fontSize = 12.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCatGridItem() {
    val sampleCat = CatEntity(
        id = "1",
        name = "Sample Cat",
        image = ImageEntity(url = "https://example.com/sample_cat.jpg")
    )

    CatGridItem(cat = sampleCat)
}
