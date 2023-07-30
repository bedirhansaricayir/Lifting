package com.lifting.app.feature_home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.lifting.app.R
import com.lifting.app.theme.Black40
import com.lifting.app.theme.PremiumPrimaryColor
import com.lifting.app.theme.grey50

@Composable
fun UserImage(
    modifier: Modifier = Modifier,
    userImage: String,
    isPremium:Boolean,
    onProfilePictureClicked: () -> Unit
) {
    Box(contentAlignment = Alignment.Center) {
        AsyncImage(
            modifier = modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
                .clickable { onProfilePictureClicked() },
            model = ImageRequest.Builder(LocalContext.current)
                .data(userImage)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = "Circular User Image",
        )
        if (isPremium) {
            Box(
                modifier = Modifier
                    .offset(x = 5.dp, y = (-15).dp)
                    .border(2.dp, Black40, CircleShape)
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .align(Alignment.TopEnd),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.crown),
                    contentDescription = "",
                    tint = Black40,
                    modifier = Modifier
                        .size(20.dp)
                        .padding(3.dp)
                )
            }
        }

    }
}