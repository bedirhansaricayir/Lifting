package com.lifting.app.feature_tracker.presentation.calendar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lifting.app.R
import com.lifting.app.theme.grey50

@Composable
fun Calendar(
    updateCurrMonthViewed: (String) -> Unit,
    updateCurrDateViewed: (String) -> Unit,
    getDateEventCount: (String) -> Int,
    checkIfDateSelected: (String) -> Boolean,
    resetCurrDate: () -> Unit,
) {
    var mvisible by remember { mutableStateOf(false) }
    var wvisible by remember { mutableStateOf(true) }

    val density = LocalDensity.current

    Column(
        modifier = Modifier
            .shadow(10.dp, RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp), clip = true)
            .clip(RoundedCornerShape(5.dp, 5.dp, 20.dp, 20.dp))
            .background(grey50)
            .padding(PaddingValues(start = 5.dp, end = 5.dp, top = 5.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AnimatedVisibility(
            visible = mvisible,
            enter = slideInVertically { with(density) { -40.dp.roundToPx() } } +
                    expandVertically(expandFrom = Alignment.Top) +
                    fadeIn(initialAlpha = 0.3f),
            exit = fadeOut() + slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            CalenderMonth(
                updateCurrMonthViewed = updateCurrMonthViewed,
                updateCurrDateViewed = updateCurrDateViewed,
                dateEventCount = getDateEventCount,
                checkIfDateSelected = checkIfDateSelected,
            )
        }

        AnimatedVisibility(
            visible = wvisible,
            enter = slideInVertically { with(density) { -40.dp.roundToPx() } } +
                    expandVertically(expandFrom = Alignment.Top) +
                    fadeIn(initialAlpha = 0f),
            exit = fadeOut() + slideOutVertically() + shrinkVertically()
        ) {
            CalendarWeek(
                updateCurrMonthViewed = updateCurrMonthViewed,
                updateCurrDateViewed = updateCurrDateViewed,
                dateEventCount = getDateEventCount,
                checkIfDateSelected = checkIfDateSelected,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                ) {
                    mvisible = !mvisible
                    wvisible = !wvisible
                    resetCurrDate.invoke()
                },
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = if (wvisible) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                contentDescription = null
            )
        }

    }
}