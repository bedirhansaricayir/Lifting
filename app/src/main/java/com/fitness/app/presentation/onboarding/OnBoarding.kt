package com.fitness.app.presentation.onboarding


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fitness.app.R
import com.fitness.app.navigation.OnBoardingPage
import com.fitness.app.ui.theme.White40

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoarding(
    onGetStartedButtonClick: () -> Unit,
    saveOnBoardingState: (completed: Boolean) -> Unit,
) {

    val items = OnBoardingPage.getData()
    val pagerState = rememberPagerState()

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
                OnBoardingItem(items = items[it])
            }
            BottomSection(
                modifier = Modifier.align(Alignment.BottomCenter).navigationBarsPadding(),
                pagerState = pagerState
            ) {
                saveOnBoardingState(true)
                onGetStartedButtonClick()
            }
        }
    }
}

@Composable
fun OnBoardingItem(items: OnBoardingPage, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = items.image),
            contentScale = ContentScale.FillHeight,
            contentDescription = "Image"
        )
        Column(modifier = modifier
            .fillMaxSize()
            .padding(bottom = 150.dp), verticalArrangement = Arrangement.Bottom) {
            Text(
                text = stringResource(id = items.title),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = modifier.padding(vertical = 8.dp))
            Text(
                text = stringResource(id = items.description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelSmall,
                color = White40,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
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
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.background,
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


