package com.swordhealth.thecat.ui.widgets

import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import com.swordhealth.thecat.R
import com.swordhealth.thecat.data.entities.CatEntity

@Composable
fun CatGrid(
    isHomeScreen: Boolean = true,
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
                        isHomeScreen = isHomeScreen,
                        cat = cat,
                        onClickDetail = onClickDetail,
                        onClickFavorite = onClickFavourite
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(276.dp))
        }
    }
}
