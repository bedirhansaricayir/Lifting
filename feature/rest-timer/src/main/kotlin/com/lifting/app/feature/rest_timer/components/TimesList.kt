package com.lifting.app.feature.rest_timer.components

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.service.getFormattedStopWatchTime
import com.lifting.app.core.ui.components.LiftingButton
import com.lifting.app.core.ui.components.LiftingButtonType
import java.util.concurrent.TimeUnit

/**
 * Created by bedirhansaricayir on 11.06.2025
 */

@Composable
internal fun TimesList(
    contentPadding: PaddingValues,
    onClickStart: (Long) -> Unit,
) {
    val context = LocalContext.current
    val times by remember {
        mutableStateOf(getTimes(context))
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = contentPadding,
        content = {
            items(times) {
                if (it is Long) {
                    val textStr by rememberSaveable(inputs = arrayOf(it)) {
                        mutableStateOf(getFormattedStopWatchTime(ms = it, spaces = false))
                    }

                    LiftingButton(
                        buttonType = LiftingButtonType.PrimaryButton(
                            text = textStr
                        ),
                        onClick = { onClickStart(it) }
                    )
                } else if (it is String) {
                    Text(
                        text = it,
                        color = LiftingTheme.colors.onBackground
                    )
                }
            }
        }
    )
}

private fun getTimes(context: Context): List<Any> = buildList {
    add(context.getString(com.lifting.app.core.ui.R.string.select_time))

    add(TimeUnit.MINUTES.toMillis(1))
    add(TimeUnit.SECONDS.toMillis(105))
    add(TimeUnit.SECONDS.toMillis(110))
    add(TimeUnit.MINUTES.toMillis(5))

    add(context.getString(com.lifting.app.core.ui.R.string.all))

    var lastI = 0
    for (i in 5..600) {
        if (i == lastI + 5) {
            add(TimeUnit.SECONDS.toMillis(i.toLong()))
            lastI = i
        }
    }
}
