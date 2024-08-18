package com.lifting.app.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.ui.R
import com.lifting.app.core.ui.extensions.lighterColor

/**
 * Created by bedirhansaricayir on 18.08.2024
 */

@Composable
fun SingleSelectableCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    name: String,
    value: String?
) {
    val isSelected = value != null

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = LiftingTheme.colors.background.lighterColor(0.1f)),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = name,
                    style = LiftingTheme.typography.subtitle1,
                    color = LiftingTheme.colors.onBackground
                )
                Text(
                    text = value ?: stringResource(id = R.string.label_select),
                    style = LiftingTheme.typography.subtitle1,
                    color = if (isSelected) LiftingTheme.colors.onBackground.copy(alpha = 0.75f)
                    else LiftingTheme.colors.primary
                )
            }

            Icon(
                imageVector = LiftingTheme.icons.forward,
                tint = LiftingTheme.colors.onBackground.copy(alpha = 0.75f),
                contentDescription = stringResource(id = R.string.label_select)
            )
        }
    }
}