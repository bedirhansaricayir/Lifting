package com.lifting.app.feature_purchase.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Badge
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lifting.app.R
import com.lifting.app.theme.Black40
import com.lifting.app.theme.grey10

@Composable
fun SelectableProductCard(
    modifier: Modifier = Modifier,
    selected: Boolean,
    title: Int,
    titleStyle: TextStyle = MaterialTheme.typography.labelMedium,
    subtitle: Int,
    subtitleStyle: TextStyle = MaterialTheme.typography.labelSmall,
    color: Color = if (selected) MaterialTheme.colorScheme.primary else grey10,
    borderWidth: Dp = 1.dp,
    borderShape: Shape = RoundedCornerShape(10.dp),
    badgeBackgroundColor: Color = MaterialTheme.colorScheme.primary,
    bagdeContentColor: Color = Black40,
    onClick: () -> Unit
) {
    val recommendedProgram = R.string.label_8_week_purchase
    Box {
        Column(
            modifier = modifier
                .wrapContentSize()
                .padding(8.dp)
                .border(
                    width = borderWidth,
                    color = color,
                    shape = borderShape
                )
                .clip(borderShape)
                .clickable {
                    onClick()
                }
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(id = title),
                style = titleStyle,
                textAlign = TextAlign.Center,
                color = color
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.padding(8.dp),
                text = stringResource(id = subtitle),
                style = subtitleStyle,
                color = color
            )

        }
        if (title == recommendedProgram) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
            ) {
                Badge(
                    modifier = Modifier,
                    backgroundColor = badgeBackgroundColor,
                    contentColor = bagdeContentColor
                ) {
                    Text(
                        text = stringResource(id = R.string.label_recommended_program),
                        color = bagdeContentColor
                    )
                }
            }
        }
    }
}