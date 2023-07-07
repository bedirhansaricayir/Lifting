package com.fitness.app.feature_auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fitness.app.feature_auth.data.model.AuthenticationState
import com.fitness.app.feature_auth.presentation.components.AuthenticationButton
import com.fitness.app.feature_auth.presentation.components.AuthenticationTitle
import com.fitness.app.feature_auth.presentation.components.TextEntryModule
import com.fitness.app.ui.theme.White40
import com.fitness.app.ui.theme.grey50

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    authenticationState: AuthenticationState,
    authenticationEvent: (AuthenticationEvent) -> Unit
) {
        Column(modifier = modifier.fillMaxSize().background(grey50), verticalArrangement = Arrangement.Center){
            AuthenticationTitle(authenticationMode = authenticationState.authenticationMode, modifier = modifier.fillMaxWidth())
            TextEntryModule(
                text = "Email",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp,0.dp,10.dp,5.dp),
                hint = "bedirhansaricayir@gmail.com",
                leadingIcon = Icons.Default.Email,
                textValue = "",
                textColor = Color.Black,
                cursorColor = MaterialTheme.colorScheme.primary,
                onValueChanged = {},
                trailingIcon = Icons.Filled.Lock,
                onTrailingIconClick = {}
            )
            TextEntryModule(
                text = "Email",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp,0.dp,10.dp,5.dp),
                hint = "bedirhansaricayir@gmail.com",
                leadingIcon = Icons.Default.Email,
                textValue = "",
                textColor = Color.Black,
                cursorColor = MaterialTheme.colorScheme.primary,
                onValueChanged = {},
                trailingIcon = Icons.Filled.Lock,
                onTrailingIconClick = {}
            )
            AuthenticationButton(modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),authenticationMode = authenticationState.authenticationMode, backgroundColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.background) {

            }
        }


}