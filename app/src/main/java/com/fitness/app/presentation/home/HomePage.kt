package com.fitness.app.presentation.home


import android.view.ViewGroup.LayoutParams
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.fitness.app.R
import com.fitness.app.core.Constants
import com.fitness.app.core.Constants.Companion.BES_GUN
import com.fitness.app.core.Constants.Companion.BES_GUN_FORM_KORU
import com.fitness.app.core.Constants.Companion.BES_GUN_KAS_KAZAN
import com.fitness.app.core.Constants.Companion.BES_GUN_YAG_YAK
import com.fitness.app.core.Constants.Companion.FORM_KORU
import com.fitness.app.core.Constants.Companion.HIC
import com.fitness.app.core.Constants.Companion.HIC_FORM_KORU
import com.fitness.app.core.Constants.Companion.HIC_KAS_KAZAN
import com.fitness.app.core.Constants.Companion.HIC_YAG_YAK
import com.fitness.app.core.Constants.Companion.IKI_UC_GUN
import com.fitness.app.core.Constants.Companion.IKI_UC_GUN_FORM_KORU
import com.fitness.app.core.Constants.Companion.IKI_UC_GUN_KAS_KAZAN
import com.fitness.app.core.Constants.Companion.IKI_UC_GUN_YAG_YAK
import com.fitness.app.core.Constants.Companion.KAS_KAZAN
import com.fitness.app.core.Constants.Companion.YAG_YAK
import com.fitness.app.data.remote.DusukZorluk
import com.fitness.app.data.remote.OrtaZorluk
import com.fitness.app.data.remote.YuksekZorluk
import com.fitness.app.presentation.calculator.SelectableGenderGroup
import com.fitness.app.presentation.calculator.SelectableGroup
import com.fitness.app.ui.theme.White40
import com.fitness.app.ui.theme.black20
import com.fitness.app.ui.theme.grey30
import com.fitness.app.ui.theme.grey50


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomePageUiState,
    onEvent: (HomePageEvent) -> Unit
) {
    val verticalScroll = rememberScrollState()
    var openBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()
    var videoUrl = remember { mutableStateOf("") }
    val openDialog = remember { mutableStateOf(false) }
    var personalizedOpenBottomSheet by remember { mutableStateOf(false) }
    val personalizedBottomSheetShate = rememberModalBottomSheetState()
    val howManyDays = listOf(HIC,IKI_UC_GUN,BES_GUN)
    var selectedHowManyDays by remember { mutableStateOf<String?>(null) }
    val whatIsYourGoal = listOf(YAG_YAK,KAS_KAZAN,FORM_KORU)
    var selectedWhatIsYourGoal by remember { mutableStateOf<String?>(null) }
    var createdPersonalizedProgram by remember { mutableStateOf<String?>(null) }
    var showButton by remember { mutableStateOf(false) }
    var showProgramDialog by remember { mutableStateOf(false) }


    if (!selectedHowManyDays.isNullOrEmpty() && !selectedWhatIsYourGoal.isNullOrEmpty()) {
        showButton = true
        when(selectedHowManyDays) {
            HIC -> {
                when(selectedWhatIsYourGoal) {
                    YAG_YAK -> {
                        createdPersonalizedProgram = HIC_YAG_YAK
                    }
                    KAS_KAZAN -> {
                        createdPersonalizedProgram = HIC_KAS_KAZAN
                    }
                    FORM_KORU -> {
                        createdPersonalizedProgram = HIC_FORM_KORU
                    }
                }
            }
            IKI_UC_GUN -> {
                when(selectedWhatIsYourGoal) {
                    YAG_YAK -> {
                        createdPersonalizedProgram = IKI_UC_GUN_YAG_YAK
                    }
                    KAS_KAZAN -> {
                        createdPersonalizedProgram = IKI_UC_GUN_KAS_KAZAN
                    }
                    FORM_KORU -> {
                        createdPersonalizedProgram = IKI_UC_GUN_FORM_KORU
                    }
                }
            }
            BES_GUN -> {
                when(selectedWhatIsYourGoal) {
                    YAG_YAK -> {
                        createdPersonalizedProgram = BES_GUN_YAG_YAK
                    }
                    KAS_KAZAN -> {
                        createdPersonalizedProgram = BES_GUN_KAS_KAZAN
                    }
                    FORM_KORU -> {
                        createdPersonalizedProgram = BES_GUN_FORM_KORU
                    }
                }
            }
        }
    }
    if (showProgramDialog) {
        if (createdPersonalizedProgram != null){
            CustomCreatedProgramDialog(
                dialogState = showProgramDialog,
                program = createdPersonalizedProgram!!
            ) {
                showProgramDialog = !showProgramDialog
            }
        }

    }

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
                videoUrl.value = it
                openDialog.value = true
            }
        }
    }

    if (openDialog.value) {
        CustomDialog(
            onDissmiss = { openDialog.value = !openDialog.value },
            dialogState = openDialog.value,
            url = videoUrl.value
        )
    }
    
    if (personalizedOpenBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { personalizedOpenBottomSheet = false },
            sheetState = personalizedBottomSheetShate,
            containerColor = black20
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = stringResource(id = R.string.PersonalizedProgramTitle),
                    color = White40,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(8.dp)
                )
            }
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Haftada Kaç gün antrenman yapıyorsun ?",
                    color = White40,
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                SelectableGroup(
                    options = howManyDays,
                    selectedOption = selectedHowManyDays,
                    onOptionSelected = { option ->
                        selectedHowManyDays = option
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Hedefin nedir ?",
                    color = White40,
                    style = MaterialTheme.typography.labelMedium
                )
                SelectableGroup(
                    options = whatIsYourGoal,
                    selectedOption = selectedWhatIsYourGoal,
                    onOptionSelected = { option ->
                        selectedWhatIsYourGoal = option
                    }
                )
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), horizontalArrangement = Arrangement.Center) {
                AnimatedVisibility(
                    visible = showButton,
                    enter = slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(
                            durationMillis = 700,
                            easing = FastOutSlowInEasing
                        )
                    )
                ) {
                    OutlinedButton(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .align(Alignment.CenterVertically),
                        onClick = { showProgramDialog = !showProgramDialog },
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.background,
                        ),
                    ) {
                        Text(
                            text = stringResource(id = R.string.Olustur),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 40.dp)
                        )
                    }
                }
            }

        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = grey50)
            .verticalScroll(verticalScroll)

    ) {
        val beginnerState = state.programData?.antrenmanlar?.dusukZorluk
        val intermediateState = state.programData?.antrenmanlar?.ortaZorluk
        val advancedState = state.programData?.antrenmanlar?.yuksekZorluk

        if (beginnerState != null) {
            Text(
                text = stringResource(id = R.string.PlanSeç),
                style = MaterialTheme.typography.titleMedium,
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), contentAlignment = Alignment.Center
            ) {
                PersonalizedProgramCard() {
                    personalizedOpenBottomSheet = !personalizedOpenBottomSheet
                }
            }
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
    if (state.isLoading == true) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(strokeWidth = 2.dp)
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

@Composable
fun CustomDialog(
    onDissmiss: () -> Unit,
    dialogState: Boolean,
    url: String
) {
    val showDialog = remember { mutableStateOf(dialogState) }


    if (showDialog.value) {
        Dialog(
            onDismissRequest = { onDissmiss.invoke() },
            properties = DialogProperties(dismissOnClickOutside = true, dismissOnBackPress = true)
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
                            onDissmiss.invoke()
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
            .fillMaxWidth(), factory = {
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

@Composable
fun CustomCreatedProgramDialog(
    dialogState: Boolean,
    program: String,
    onDissmiss: () -> Unit
) {
    val showDialog = remember { mutableStateOf(dialogState) }
    val verticalScroll = rememberScrollState()
    if (showDialog.value) {
        Dialog(
            onDismissRequest = { onDissmiss.invoke() },
            properties = DialogProperties(dismissOnClickOutside = true, dismissOnBackPress = true)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(verticalScroll),
                shape = MaterialTheme.shapes.large,
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = grey30
                ),
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 12.dp, horizontal = 8.dp),
                    text = stringResource(id = R.string.KişiselProgram),
                    style = MaterialTheme.typography.titleSmall
                )
                Box(modifier = Modifier) {
                    Text(modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp),text = program, style = MaterialTheme.typography.labelMedium,color = White40)
                }
            }
        }
    }
}
