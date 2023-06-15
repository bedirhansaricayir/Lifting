package com.fitness.app.presentation.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fitness.app.R
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.log

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState().value
    Log.d("comingResponse", state.programData.toString())
    Column(modifier = Modifier.fillMaxSize()) {
        state.programData?.antrenmanlar?.dusukZorluk?.forEach {
            Text(text = it.programAdi.toString())
        }
    }


}
