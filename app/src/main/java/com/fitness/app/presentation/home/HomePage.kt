package com.fitness.app.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel(),state: HomePageUiState) {
    Column(modifier = Modifier.fillMaxSize()) {
        state.programData?.antrenmanlar?.dusukZorluk?.forEach {
            Text(text = it.programAdi.toString())
        }
    }


}
