package com.lifting.app.feature_auth.presentation.email_verification

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lifting.app.R
import com.lifting.app.feature_auth.domain.model.AuthenticationMode
import com.lifting.app.feature_auth.presentation.AuthenticationEvent
import com.lifting.app.feature_auth.presentation.AuthenticationState
import com.lifting.app.feature_auth.presentation.components.AuthenticationButton
import com.lifting.app.feature_auth.presentation.components.AuthenticationTitle
import com.lifting.app.theme.grey50

@Composable
fun VerificationScreen(
    modifier: Modifier = Modifier,
    authenticationState: AuthenticationState,
    authenticationEvent: (AuthenticationEvent) -> Unit,
    isEmailVerified: () -> Unit,
) {

    LaunchedEffect(key1 = authenticationState.isEmailVerified) {
        if (authenticationState.isEmailVerified) {
            authenticationEvent(AuthenticationEvent.OnSignInSuccessful(true))
            authenticationEvent(AuthenticationEvent.AddUserToFirestore)
            isEmailVerified()
        }
    }
    VerificationScreenContent(
        modifier = modifier,
        authenticationMode = AuthenticationMode.VERIFICATION,
        onReload = { authenticationEvent(AuthenticationEvent.ReloadFirebaseUser) }
    )
}

@Composable
fun VerificationScreenContent(
    modifier: Modifier = Modifier,
    authenticationMode: AuthenticationMode,
    onReload: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(grey50)
            .systemBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        Spacer(modifier = modifier.weight(0.5f))
        Image(
            modifier = modifier.size(100.dp),
            painter = painterResource(id = R.drawable.send_mail),
            contentDescription = "Send Email Image",
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary)
        )
        Spacer(modifier = modifier.weight(0.5f))
        AuthenticationTitle(
            authenticationMode = authenticationMode,
            textStyle = MaterialTheme.typography.headlineSmall,
            modifier = modifier.fillMaxWidth().padding(8.dp)
        )
        Spacer(modifier = modifier.height(16.dp))
        AuthenticationTitle(
            authenticationMode = authenticationMode,
            modifier = modifier.fillMaxWidth().padding(8.dp),
            isSubTitle = true
        )
        Spacer(modifier = modifier.weight(1f))
        AuthenticationButton(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            authenticationMode = authenticationMode,
            enabledAuthentication = true,
            onAuthenticate = onReload
        )
        Spacer(modifier = Modifier.weight(0.5f))
    }
}