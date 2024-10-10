package com.swordhealth.thecat.ui.widgets

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.swordhealth.thecat.data.entities.CatEntity
import com.swordhealth.thecat.data.entities.FavoriteEntity

@Composable
fun CatGrid(
    modifier: Modifier = Modifier,
    listLazy: LazyPagingItems<CatEntity>? = null,
    onClickDetail: (CatEntity) -> Unit = {},
    onClickFavourite: (CatEntity) -> Unit = {}
) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        columns = GridCells.Fixed(3),
    ) {
        listLazy?.let {
            items(listLazy.itemCount) { index ->
                val cat = listLazy[index]

                if (cat != null) {
                    CatGridItem(
                        cat = cat,
                        isHomeScreen = true,
                        onClickDetail = onClickDetail,
                        onClickFavorite = onClickFavourite
                    )
                }
            }
        } ?: run {
      /*      list?.let {
                items(list.size) { index ->
                    val favoriteEntity = list[index]

                    val textInformations = favoriteEntity.name?.split(".") ?: emptyList()

                    val nameFavorite = if (textInformations.isNotEmpty()) textInformations[0] else ""
                    val lifeSpanFavorite = if (textInformations.size > 1) textInformations[1] else ""

                    val lifeSpanRange = lifeSpanFavorite.split("-").mapNotNull { it.trim().toIntOrNull() }

                    val averageLifeSpan = if (lifeSpanRange.size == 2) {
                        (lifeSpanRange[0] + lifeSpanRange[1]) / 2
                    } else {
                        0
                    }

                    val cat = CatEntity(
                        idFavorite = favoriteEntity.id,
                        name = nameFavorite,
                        image = favoriteEntity.image,
                        lifeSpan = "$averageLifeSpan"
                    )

                    CatGridItem(cat = cat,  onClickFavorite = onClickFavourite)
                }
            }*/
        }

        item {
            Spacer(modifier = Modifier.height(276.dp))
        }
    }
}
