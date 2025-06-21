package com.lifting.app.feature.calendar.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.ui.components.LiftingButton
import com.lifting.app.core.ui.components.LiftingButtonType

/**
 * Created by bedirhansaricayir on 15.06.2025
 */

@Composable
fun EmptyCalendarItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(LiftingTheme.dimensions.small)
    ) {
        Text(
            text = "Antrenman Bulunamadı",
            style = LiftingTheme.typography.subtitle1,
            color = LiftingTheme.colors.onBackground
        )

        Text(
            text = "Yeni bir antrenmana başlamak için tıklayın",
            style = LiftingTheme.typography.subtitle2,
            color = LiftingTheme.colors.onBackground.copy(alpha = 0.75f)
        )
        LiftingButton(
            buttonType = LiftingButtonType.TextButton(
                text = "Yeni antrenman oluştur",
            ),
            onClick = onClick
        )
    }

}