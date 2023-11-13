package com.lifting.app.feature_profile.presentation

import android.widget.Toast
import androidx.annotation.StringRes
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.lifting.app.R
import com.lifting.app.common.components.CommonAlertDialog
import com.lifting.app.common.components.CommonProgressIndicatior
import com.lifting.app.common.util.shimmerLoadingAnimation
import com.lifting.app.feature_home.presentation.components.CommonTopBar

@Composable
fun AccountInformationScreen(
    state: ProfileScreenState,
    profileScreenEvent: (ProfileScreenEvent) -> Unit,
    onBackNavigationIconClicked: () -> Unit,
) {

    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = state.profileDataState.accountDeleteError) {
        if (state.profileDataState.accountDeleteError) {
            Toast.makeText(
                context,
                context.getString(R.string.delete_account_error_label),
                Toast.LENGTH_SHORT
            ).show()
            profileScreenEvent(ProfileScreenEvent.OnUserViewedTheError)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CommonTopBar(
            title = R.string.account_information,
            onBackNavigationIconClicked = onBackNavigationIconClicked
        )
        InformationScreenUserImage(image = state.profileDataState.profilePictureUrl)

        Spacer(modifier = Modifier.height(16.dp))
        InformationScreenInfo(info = R.string.Username, text = state.profileDataState.username)
        InformationScreenInfo(info = R.string.Email, text = state.profileDataState.email)
        InformationScreenInfo(
            info = R.string.membership_date,
            text = state.profileDataState.createdAt
        )
        Spacer(modifier = Modifier.weight(1f))
        DeleteAccountButton(onClicked = { showDialog = true })

        CommonAlertDialog(
            dialogState = showDialog,
            title = R.string.delete_account_dialog_title,
            body = R.string.delete_account_dialog_description,
            confirmButtonTitle = R.string.delete_button_label,
            confirmButtonColor = Color.Red,
            onDissmiss = { showDialog = false },
            onCancelClicked = { showDialog = false },
            onConfirmClicked = { profileScreenEvent(ProfileScreenEvent.OnDeleteAccountClicked) }
        )
    }
    if (state.profileDataState.accountDeleteLoading) {
        DeleteAccountLoadingSection()
    }
}

@Composable
fun InformationScreenUserImage(
    image: String?
) {
    var isLoaded by remember {
        mutableStateOf(false)
    }
        AsyncImage(
            model = image ?:"", contentDescription = "User Image", contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp)
                .clip(
                    RoundedCornerShape(16.dp)
                )
                .shimmerLoadingAnimation(isLoadingCompleted = isLoaded)
            ,
            onLoading = {
                isLoaded = false
            },
            onSuccess = {
                isLoaded = true
            }
        )

}

@Composable
fun InformationScreenInfo(
    @StringRes info: Int,
    text: String?
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            modifier = Modifier,
            text = stringResource(id = info),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        text?.let {
            Text(
                modifier = Modifier,
                text = it,
                style = MaterialTheme.typography.labelMedium,
                color = Color.LightGray
            )
        }

    }
}

@Composable
fun DeleteAccountButton(
    onClicked: () -> Unit
) {
    TextButton(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(), onClick = onClicked,
        colors = ButtonDefaults.textButtonColors(
            contentColor = Color.Red
        )
    ) {
        Text(
            text = stringResource(id = R.string.delete_account),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
fun DeleteAccountLoadingSection() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CommonProgressIndicatior()
    }
}