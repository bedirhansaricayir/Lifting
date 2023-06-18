package com.fitness.app.presentation.home


import android.view.ViewGroup.LayoutParams
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.fitness.app.R
import com.fitness.app.data.remote.DusukZorluk
import com.fitness.app.data.remote.OrtaZorluk
import com.fitness.app.data.remote.YuksekZorluk
import com.fitness.app.ui.theme.White40
import com.fitness.app.ui.theme.black20
import com.fitness.app.ui.theme.grey30
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomePageUiState,
    onEvent: (HomePageEvent) -> Unit
) {
    val context = LocalContext.current
    val verticalScroll = rememberScrollState()
    var openBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState =
        rememberModalBottomSheetState()
    var videoUrl = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val openDialog = remember { mutableStateOf(false) }


    if (openBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { openBottomSheet = false },
            sheetState = bottomSheetState,
            containerColor = black20
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = state.selectedProgramName!!,
                    color = White40,
                    style = MaterialTheme.typography.titleSmall
                )
            }
            BottomSheetContent(state.selectedProgram) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                videoUrl.value = it
                scope.launch {
                    bottomSheetState.hide()
                }
                openDialog.value = true
            }
        }
    }

    if (openDialog.value) {
        CustomDialog(
            dialogState = true,
            url = videoUrl.value
        )
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
            BeginnerProgramList(
                state = beginnerState,
                programLevel = R.string.BeginnerProgram
            ) {
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

@Composable
fun CustomDialog(
    dialogState: Boolean,
    url: String
) {
    val showDialog = remember { mutableStateOf(dialogState) }


    if (showDialog.value) {
        Dialog(
            onDismissRequest = { showDialog.value = false },
            properties = DialogProperties(dismissOnClickOutside = false, dismissOnBackPress = true)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.65f),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(
                    containerColor = grey30
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    ComposableWebView(url = url)
                    Spacer(modifier = Modifier.height(24.dp))
                        TextButton(
                            onClick = {
                                showDialog.value = false
                            },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("Kapat")
                        }


                }


            }
        }
    }

}

@Composable
fun ComposableWebView(url: String) {
    var webView: WebView? = null

    AndroidView(
        modifier = Modifier
            .fillMaxHeight(0.70F)
            .fillMaxWidth(),factory = {
        WebView(it).apply {
            webViewClient = WebViewClient()
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
            settings.javaScriptEnabled = true
            loadUrl(url)
            webView = this
            setOnTouchListener { _, _ -> true }


        }
    }, update = {
        webView = it
        it.loadUrl(url)
    })

}

