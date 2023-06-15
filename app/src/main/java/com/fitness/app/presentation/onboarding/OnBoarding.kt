package com.fitness.app.presentation.onboarding


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fitness.app.R
import com.fitness.app.navigation.OnBoardingPage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoarding(
    onGetStartedButtonClick: () -> Unit,
    onBoardingViewModel: OnBoardingViewModel = hiltViewModel()
) {

    val items = OnBoardingPage.getData()
    val pagerState = rememberPagerState()
    var onClickGetStarted by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            HorizontalPager(
                pageCount = items.size,
                state = pagerState
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = items[it].image),
                    contentDescription = "Image"
                )
            }
            BottomSection(
                modifier = Modifier.align(Alignment.BottomCenter),
                pagerState = pagerState
            ) {
                onBoardingViewModel.saveOnBoardingState(completed = true)
                onClickGetStarted = true
                onGetStartedButtonClick()
            }
        }


    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomSection(modifier: Modifier, pagerState: PagerState, onButtonClick: () -> Unit = {}) {
    Row(
        modifier = modifier.padding(horizontal = 40.dp, vertical = 40.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            modifier = modifier.fillMaxWidth(),
            visible = pagerState.currentPage == 2
        ) {
            OutlinedButton(
                modifier = modifier
                    .fillMaxWidth(0.7f)
                    .align(Alignment.Bottom),
                onClick = { onButtonClick() },
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.primary
                ),
            ) {
                Text(
                    text = stringResource(id = R.string.GetStarted),
                    textAlign = TextAlign.Center,
                    modifier = modifier.padding(vertical = 8.dp, horizontal = 40.dp)
                )
            }
        }
    }
}


