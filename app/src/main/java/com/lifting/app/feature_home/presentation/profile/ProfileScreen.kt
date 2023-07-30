package com.lifting.app.feature_home.presentation.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.lifting.app.R
import com.lifting.app.common.components.CommonProgressIndicatior
import com.lifting.app.feature_home.domain.repository.ProfileSettingsData
import com.lifting.app.feature_home.presentation.components.RoundedCornersSurface
import com.lifting.app.theme.Black40
import com.lifting.app.theme.White40
import com.lifting.app.theme.grey50
import com.lifting.app.theme.white10

@Composable
fun ProfileScreen(
    state: ProfileScreenState,
    profileScreenEvent: (ProfileScreenEvent) -> Unit,
    onBackNavigationIconClicked: () -> Unit,
    onForwardNavigationIconClicked: (route: Int) -> Unit
) {

    LaunchedEffect(key1 = state.addImageToStorage, block = {
        state.addImageToStorage?.let {
            profileScreenEvent(ProfileScreenEvent.OnProfilePictureAddedToStorage(it))
        }

    })
    ProfileScreenContent(
        profileScreenState = state,
        onProfilePictureClicked = {
            profileScreenEvent(
                ProfileScreenEvent.OnProfilePictureSelected(
                    it
                )
            )
        },
        onBackNavigationIconClicked = onBackNavigationIconClicked,
        onForwardNavigationIconClicked = { onForwardNavigationIconClicked(it) }
    )
}

@Composable
fun ProfileScreenContent(
    modifier: Modifier = Modifier,
    profileScreenState: ProfileScreenState,
    onProfilePictureClicked: (uri: Uri) -> Unit,
    onBackNavigationIconClicked: () -> Unit,
    onForwardNavigationIconClicked: (route: Int) -> Unit
) {

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let { onProfilePictureClicked(it) }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(grey50),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        RoundedCornersSurface(modifier = Modifier) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Black40), verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(modifier = Modifier, onClick = onBackNavigationIconClicked) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                        contentDescription = "Back Button",
                        tint = Color.White
                    )
                }
                Text(
                    modifier = Modifier,
                    text = stringResource(id = R.string.label_profile_screen),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = White40
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        profileScreenState.profileDataState.let { user ->
            ProfilePhotoSection(photo = user.profilePictureUrl) {
                singlePhotoPickerLauncher.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            ProfileUserSection(username = user.username, email = user.email)
        }

        Card(
            modifier = Modifier
                .statusBarsPadding()
                .wrapContentSize()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Black40, contentColor = White40),
            shape = RoundedCornerShape(8.dp)
        ) {
            LazyColumn() {
                items(profileScreenState.settings.size) {
                    ProfileSettingsItem(
                        data = profileScreenState.settings[it],
                        onClick = { route ->
                            onForwardNavigationIconClicked(route)
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun ProfilePhotoSection(
    modifier: Modifier = Modifier,
    photo: String?,
    galleryLauncher: () -> Unit
) {
    var imageState by remember { mutableStateOf(false) }
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        AsyncImage(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape),
            model = photo ?: "",
            contentDescription = "User Image",
            contentScale = ContentScale.Crop,
            onLoading = {
                imageState = false
            },
            onSuccess = { imageState = true },
        )
        if (!imageState) {
            CommonProgressIndicatior(strokeWidth = 2.dp)
        }
        if (imageState) {
            Box(
                modifier = Modifier
                    .border(2.dp, grey50, CircleShape)
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .align(Alignment.BottomEnd),
                contentAlignment = Alignment.Center
            )
            {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(25.dp)
                        .padding(3.dp)
                        .clickable {
                            galleryLauncher()
                        }
                )
            }
        }
    }
}

@Composable
fun ProfileUserSection(modifier: Modifier = Modifier, username: String?, email: String?) {
    Text(
        modifier = Modifier,
        text = username ?: "",
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
        color = White40
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        modifier = Modifier,
        text = email ?: "",
        style = MaterialTheme.typography.labelMedium,
        color = white10
    )

}

@Composable
fun ProfileSettingsItem(
    modifier: Modifier = Modifier,
    data: ProfileSettingsData,
    onClick: (title: Int) -> Unit
) {

    Column(
        Modifier
            .fillMaxSize()
            .clickable(role = Role.Button) { onClick(data.itemTitle) }
            .padding(horizontal = 16.dp, vertical = 8.dp)

    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = data.leadingIcon),
                contentDescription = data.leadingIconContentDescription
            )
            Text(
                text = stringResource(id = data.itemTitle), modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            )
            Spacer(Modifier.width(16.dp))
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                contentDescription = data.trailingIconContentDescription,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}