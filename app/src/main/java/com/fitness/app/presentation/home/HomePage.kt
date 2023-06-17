package com.fitness.app.presentation.home


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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fitness.app.R
import com.fitness.app.data.remote.DusukZorluk
import com.fitness.app.data.remote.OrtaZorluk
import com.fitness.app.data.remote.YuksekZorluk
import com.fitness.app.ui.theme.White40
import com.fitness.app.ui.theme.grey30


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomePageUiState,
    onEvent: (HomePageEvent) -> Unit
) {
    val verticalScroll = rememberScrollState()
    var openBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState =
        rememberModalBottomSheetState()

    if (openBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { openBottomSheet = false },
            sheetState = bottomSheetState,
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = state.selectedProgramName!!)
            }
            BottomSheetContent()
            state.selectedProgram.let {
                it.forEach {
                    Text(text = it.gun.toString())
                    it.hareketler.forEach {
                        Text(text = it.hareketAdi.toString())
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = grey30)
            .verticalScroll(verticalScroll)

    ) {
        val beginnerState = state.programData?.antrenmanlar?.dusukZorluk
        val intermediateState = state.programData?.antrenmanlar?.ortaZorluk
        val advancedState = state.programData?.antrenmanlar?.yuksekZorluk

        if (beginnerState != null) {
            BeginnerProgramList(state = beginnerState, programLevel = R.string.BeginnerProgram) {
                openBottomSheet = !openBottomSheet
                onEvent(
                    HomePageEvent.OnWorkoutProgramPlayButtonClicked(
                        it.uygulanis,
                        it.programAdi!!
                    )
                )
            }
            IntermediateProgramList(
                programListData = intermediateState!!,
                programLevel = R.string.IntermediateProgram
            ) {
                openBottomSheet = !openBottomSheet
                onEvent(
                    HomePageEvent.OnWorkoutProgramPlayButtonClicked(
                        it.uygulanis,
                        it.programAdi!!
                    )
                )
            }
            AdvancedProgramList(
                programListData = advancedState!!,
                programLevel = R.string.AdvancedProgram
            ) {
                openBottomSheet = !openBottomSheet
                onEvent(
                    HomePageEvent.OnWorkoutProgramPlayButtonClicked(
                        it.uygulanis,
                        it.programAdi!!
                    )
                )
            }
        }
    }
}

@Composable
fun BeginnerProgramList(
    modifier: Modifier = Modifier,
    state: ArrayList<DusukZorluk>,
    @StringRes
    programLevel: Int,
    onButtonClicked: (DusukZorluk) -> Unit
) {
    Text(
        text = stringResource(id = programLevel),
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.titleLarge,
        color = White40,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    )

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
            items(state) { model ->
                BeginnerProgramCard(model = model) {
                    onButtonClicked(it)
                }
            }
        }
    }
}

@Composable
fun IntermediateProgramList(
    modifier: Modifier = Modifier,
    programListData: ArrayList<OrtaZorluk>,
    @StringRes
    programLevel: Int,
    onButtonClicked: (OrtaZorluk) -> Unit
) {
    Text(
        text = stringResource(id = programLevel),
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.titleLarge,
        color = White40,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    )

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
            items(programListData) { model ->
                IntermediateProgramCard(model = model) {
                    onButtonClicked(it)
                }
            }
        }
    }
}

@Composable
fun AdvancedProgramList(
    modifier: Modifier = Modifier,
    programListData: ArrayList<YuksekZorluk>,
    @StringRes
    programLevel: Int,
    onButtonClicked: (YuksekZorluk) -> Unit
) {
    Text(
        text = stringResource(id = programLevel),
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.titleLarge,
        color = White40,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    )

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
            items(programListData) { model ->
                AdvancedProgramCard(model = model) {
                    onButtonClicked(it)
                }
            }
        }
    }
}