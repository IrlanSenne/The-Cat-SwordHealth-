package com.swordhealth.thecat.ui.widgets

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun CatsBottomBar(
    list: List<CatsBarItem>,
    selectedIndex: Int,
    onSelectedIndexValueChange: (Int) -> Unit
) {

    NavigationBar {
        list.forEachIndexed { index, catsBarItem ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = {
                    onSelectedIndexValueChange(index)
                },
                icon = {
                    Icon(
                        imageVector = catsBarItem.Icon,
                        contentDescription = "Icon"
                    )
                },
                label = {
                    Text(catsBarItem.label)
                }
            )
        }
    }
}

data class CatsBarItem(
    val label: String,
    val Icon: ImageVector
)