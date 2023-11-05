package com.lifting.app.feature_home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lifting.app.common.util.shimmerLoadingAnimation
import com.lifting.app.theme.grey50

@Composable
fun HomeScreenPreview() {
    var isLoadingCompleted by remember { mutableStateOf(false) }
    var isLightModeActive by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(grey50)
    ) {
        SignedInUserSectionPreview(isLoadingCompleted, isLightModeActive)
        PlanSectionPreview(isLoadingCompleted, isLightModeActive)
        PersonalizedProgramCardPreview(isLoadingCompleted, isLightModeActive)
        PlanSectionPreview(isLoadingCompleted, isLightModeActive)
        PersonalizedProgramCardPreview(isLoadingCompleted, isLightModeActive)

    }
}

@Composable
fun SignedInUserSectionPreview(
    isLoadingCompleted: Boolean,
    isLightModeActive: Boolean,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ComponentCircle(isLoadingCompleted, isLightModeActive)
        Column(
            modifier = Modifier
                .padding(start = 16.dp), verticalArrangement = Arrangement.Center
        ) {
            ComponentRectangleLineShort(isLoadingCompleted, isLightModeActive)
            Spacer(modifier = Modifier.height(2.dp))
            ComponentRectangleLineLong(isLoadingCompleted, isLightModeActive)
        }

    }

}

@Composable
fun PlanSectionPreview(
    isLoadingCompleted: Boolean,
    isLightModeActive: Boolean,
) {
    ComponentRectangleLineTitle(isLoadingCompleted, isLightModeActive)
}

@Composable
fun PersonalizedProgramCardPreview(
    isLoadingCompleted: Boolean,
    isLightModeActive: Boolean,
) {
    ComponentRectangle(isLoadingCompleted, isLightModeActive)
}

@Composable
fun ComponentCircle(
    isLoadingCompleted: Boolean,
    isLightModeActive: Boolean,
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(color = Color.LightGray)
            .size(50.dp)
            .shimmerLoadingAnimation(isLoadingCompleted, isLightModeActive)
    )
}

@Composable
fun ComponentRectangleLineLong(
    isLoadingCompleted: Boolean,
    isLightModeActive: Boolean,
) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
            .size(height = 30.dp, width = 200.dp)
            .shimmerLoadingAnimation(isLoadingCompleted, isLightModeActive)
    )
}

@Composable
fun ComponentRectangleLineShort(
    isLoadingCompleted: Boolean,
    isLightModeActive: Boolean,
) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
            .size(height = 30.dp, width = 100.dp)
            .shimmerLoadingAnimation(isLoadingCompleted, isLightModeActive)
    )
}

@Composable
fun ComponentRectangleLineTitle(
    isLoadingCompleted: Boolean,
    isLightModeActive: Boolean,
) {
    Box(modifier = Modifier.padding(8.dp)) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(8.dp))
                .background(color = Color.LightGray)
                .size(height = 30.dp, width = 300.dp)
                .shimmerLoadingAnimation(isLoadingCompleted, isLightModeActive)
        )
    }

}

@Composable
fun ComponentRectangle(
    isLoadingCompleted: Boolean,
    isLightModeActive: Boolean,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(8.dp))
                .background(color = Color.LightGray)
                .height(250.dp)
                .fillMaxWidth()
                .shimmerLoadingAnimation(isLoadingCompleted, isLightModeActive)

        )
    }

}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreviewPreview() {
    HomeScreenPreview()
}