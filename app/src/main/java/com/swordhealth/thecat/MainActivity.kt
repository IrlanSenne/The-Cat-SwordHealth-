package com.swordhealth.thecat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import com.swordhealth.thecat.ui.screens.CatListScreen
import com.swordhealth.thecat.ui.screens.FavoritesCatsScreen
import com.swordhealth.thecat.ui.widgets.CatsBarItem
import com.swordhealth.thecat.ui.widgets.CatsBottomBar
import com.swordhealth.thecat.ui.widgets.CatsLoading
import com.swordhealth.thecat.ui.theme.TheCatTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isLoading by remember { mutableStateOf(false) }
            var selectedIndex by remember { mutableStateOf(0) }

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
                        ) { selectedIndex = it }

                    }) { innerPadding ->
                    when (selectedIndex) {
                        0 -> CatListScreen(mainViewModel = mainViewModel) { isLoading = it }
                        1 -> FavoritesCatsScreen(mainViewModel = mainViewModel)
                    }

                    CatsLoading(isLoading)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
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
