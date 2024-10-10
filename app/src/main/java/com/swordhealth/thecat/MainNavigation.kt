package com.swordhealth.thecat

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.swordhealth.thecat.data.entities.CatUI
import com.swordhealth.thecat.ui.screens.CatDetailScreen
import com.swordhealth.thecat.ui.screens.CatHomeScreen
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

@Composable
fun MainNavigation(mainViewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = CatRoutes.catHomeScreen) {
        composable(route = CatRoutes.catHomeScreen) {
            CatHomeScreen(mainViewModel, navController)
        }
        composable(
            route = "${CatRoutes.catDetailScreen}/{catEntityJson}",
            arguments = listOf(navArgument("catEntityJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val catEntityJson = backStackEntry.arguments?.getString("catEntityJson")
            val catEntity = catEntityJson?.let { Json.decodeFromString<CatUI>(it) }

            catEntity?.let {
                CatDetailScreen(mainViewModel, navController, it)
            }
        }
    }
}

fun <T> navigateWithObject(
    navController: NavHostController?,
    route: String,
    item: T,
    serializer: KSerializer<T>
) {
    val itemJson = Json.encodeToString(serializer, item)
    val encodedItemJson = Uri.encode(itemJson)

    navController?.navigate("$route/$encodedItemJson")
}

object CatRoutes {
    const val catHomeScreen = "cat_home_screen"
    const val catDetailScreen = "cat_detail_screen"
}
