package com.lifting.app.feature_detail.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.lifting.app.R
import com.lifting.app.feature_detail.domain.model.SelectedProgram
import com.lifting.app.feature_detail.presentation.components.BackButton
import com.lifting.app.feature_home.data.remote.model.Hareketler
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProgramScreen(
    program: SelectedProgram,
    onItemClick: (String) -> Unit,
    onBackNavigationIconClicked: () -> Unit
) {
    val pagerState = rememberPagerState { program.programDay }
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.advanced_image),
                contentDescription = "Program Image"
            )
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.BottomStart),
                text = program.programName,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            BackButton(
                modifier = Modifier.align(Alignment.TopStart),
                onBackNavigationIconClicked = onBackNavigationIconClicked
            )
        }
        TabRow(modifier = Modifier, selectedTabIndex = pagerState.currentPage) {
            program.program.forEachIndexed { index, uygulanis ->
                Tab(selected = index == pagerState.currentPage,
                    onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                    text = {
                        Text(
                            text = uygulanis.gun!!,
                            style = MaterialTheme.typography.labelSmall
                        )
                    })

            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { index ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    program.program[index].hareketler.forEach { model ->
                        ProgramItem(model = model, onItemClick = onItemClick)
                    }
                }
            }
        }
    }
}

@Composable
fun ProgramItem(
    modifier: Modifier = Modifier,
    model: Hareketler,
    onItemClick: (String) -> Unit
) {
    val request = ImageRequest.Builder(LocalContext.current)
        .data(model.thumbnail ?: R.drawable.unavailable)
        .crossfade(true)
        .build()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                if (model.video != null) {
                    onItemClick(model.video!!)
                }
            }
            .padding(16.dp)
    ) {
        AsyncImage(
            modifier = modifier
                .size(80.dp, 80.dp)
                .clip(RoundedCornerShape(16.dp)),
            model = request,
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            contentDescription = "Movement Image",
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                text = model.hareketAdi!!,
                modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = buildString {
                    append(model.setSayisi)
                    append(" SETS • ")
                    append(model.tekrarSayisi)
                    if (model.agirlikYuzdesi != null) {
                        append(" REPS • ")
                        append(model.agirlikYuzdesi)
                        append("%")
                    } else {
                        append(" REPS")
                    }

                },
                modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                color = Color.White.copy(0.5f),
            )
        }
    }
}