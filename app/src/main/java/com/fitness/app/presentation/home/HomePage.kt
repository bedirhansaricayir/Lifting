package com.fitness.app.presentation.home

import android.widget.Toast
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fitness.app.R
import com.fitness.app.data.remote.DusukZorluk
import com.fitness.app.ui.theme.White40
import com.fitness.app.ui.theme.grey30


@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel(), state: HomePageUiState) {
    val context = LocalContext.current
    val verticalScroll = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = grey30)
            .verticalScroll(verticalScroll)

    ) {
        val programData = state.programData?.antrenmanlar?.dusukZorluk
        if (programData != null) {
            ProgramList(programListData = programData, programLevel = R.string.BeginnerProgram) {
                Toast.makeText(context, "Button Clicked!", Toast.LENGTH_SHORT).show()
            }
            ProgramList(programListData = programData, programLevel = R.string.IntermediateProgram) {
                Toast.makeText(context, "Button Clicked!", Toast.LENGTH_SHORT).show()
            }
            ProgramList(programListData = programData, programLevel = R.string.AdvancedProgram) {
                Toast.makeText(context, "Button Clicked!", Toast.LENGTH_SHORT).show()
            }
        }

    }


}

@Composable
fun ProgramList(
    modifier: Modifier = Modifier,
    programListData: ArrayList<DusukZorluk>,
    @StringRes
    programLevel: Int,
    onClickEvent: () -> Unit
) {
    Text(text = stringResource(id = programLevel), textAlign = TextAlign.Start, style = MaterialTheme.typography.titleLarge,color = White40, modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp))

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LazyRow(
            modifier = modifier.fillMaxHeight()
        ) {
            items(programListData) {
                ProgramCard(model = it) {
                    onClickEvent()
                }
            }
        }
    }
}