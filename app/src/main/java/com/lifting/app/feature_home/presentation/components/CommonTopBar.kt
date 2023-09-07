package com.lifting.app.feature_home.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lifting.app.R
import com.lifting.app.theme.White40

@Composable
fun CommonTopBar(
    modifier : Modifier = Modifier,
    @DrawableRes backButtonIcon: Int = R.drawable.baseline_arrow_back_ios_new_24,
    @StringRes title: Int,
    onBackNavigationIconClicked: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(modifier = Modifier.padding(end = 8.dp), onClick = onBackNavigationIconClicked) {
            Icon(
                painter = painterResource(id = backButtonIcon),
                contentDescription = "Back Button",
                tint = Color.White
            )
        }
        Text(
            modifier = Modifier,
            text = stringResource(id = title),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = White40
        )
    }
}