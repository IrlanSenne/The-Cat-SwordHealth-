package com.swordhealth.thecat.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.swordhealth.thecat.MainViewModel
import com.swordhealth.thecat.data.entities.CatUI


@Composable
fun CatDetailScreen(
    mainViewModel: MainViewModel? = null,
    navController: NavHostController? = null,
    catUI: CatUI?
) {

  Column(
      modifier = Modifier
          .fillMaxSize()
          .verticalScroll(rememberScrollState())
  ) {
      Text("${catUI}")
  }
}