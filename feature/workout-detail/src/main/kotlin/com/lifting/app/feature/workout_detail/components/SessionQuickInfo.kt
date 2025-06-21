package com.lifting.app.feature.workout_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.pluralStringResource
import com.lifting.app.core.common.extensions.EMPTY
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.ui.common.toWeightUnitPreferencesString
import com.lifting.app.core.ui.extensions.toLocalizedDuration

/**
 * Created by bedirhansaricayir on 06.04.2025
 */

@Composable
internal fun SessionQuickInfo(
    duration: Long?,
    volume: Double?,
    prs: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(LiftingTheme.dimensions.small)
    ){
        duration?.let {
            SessionQuickInfoItem(
                icon = LiftingTheme.icons.timer,
                text = it.toLocalizedDuration()
            )
        }
        volume?.let {
            SessionQuickInfoItem(
                icon = LiftingTheme.icons.weight,
                text = volume.toWeightUnitPreferencesString(addUnitSuffix = true, spaceBeforeSuffix = true)
            )
        }
        if (prs > 0) {
            SessionQuickInfoItem(
                icon = LiftingTheme.icons.trophy,
                text = pluralStringResource(
                    id = com.lifting.app.core.ui.R.plurals.number_of_prs,
                    count = prs,
                    prs
                )
            )
        }
    }
}

@Composable
private fun SessionQuickInfoItem(
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(LiftingTheme.dimensions.extraSmall)
    ){
        Icon(
            modifier = Modifier.size(LiftingTheme.dimensions.large),
            imageVector = icon,
            contentDescription = String.EMPTY,
            tint = LiftingTheme.colors.onBackground.copy(0.75f)
        )
        Text(
            text = text,
            style = LiftingTheme.typography.caption,
            color = LiftingTheme.colors.onBackground.copy(0.75f)
        )
    }
}