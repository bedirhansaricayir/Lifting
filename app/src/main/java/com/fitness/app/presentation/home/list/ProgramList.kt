package com.fitness.app.presentation.home.list

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fitness.app.data.remote.DusukZorluk
import com.fitness.app.data.remote.OrtaZorluk
import com.fitness.app.data.remote.YuksekZorluk
import com.fitness.app.presentation.home.list.card.AdvancedProgramCard
import com.fitness.app.presentation.home.list.card.BeginnerProgramCard
import com.fitness.app.presentation.home.list.card.IntermediateProgramCard
import com.fitness.app.ui.theme.White40

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
        style = MaterialTheme.typography.titleMedium,
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
        style = MaterialTheme.typography.titleMedium,
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
        style = MaterialTheme.typography.titleMedium,
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