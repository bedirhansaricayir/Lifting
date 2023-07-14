package com.lifting.app.feature_auth.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun VerificationScreen(
    modifier: Modifier =  Modifier,
    authenticationState: AuthenticationState,
    authenticationEvent: (AuthenticationEvent) -> Unit,
    isEmailVerified: () -> Unit,
) {

    LaunchedEffect(key1 = authenticationState.isEmailVerified){

        if (authenticationState.isEmailVerified) {
            isEmailVerified()
        }
    }
    VerificationScreenContent(
        modifier = modifier,
        onReload = { authenticationEvent(AuthenticationEvent.ReloadFirebaseUser) }
    )
}

@Composable
fun VerificationScreenContent(
    modifier: Modifier,
    onReload: () -> Unit
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = onReload ) {
            Text(text = "Already Verified?")
        }
    }
}