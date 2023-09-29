package com.lifting.app.feature_home.presentation.home.dialog

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.lifting.app.R
import com.lifting.app.feature_home.presentation.components.YoutubePlayer
import com.lifting.app.theme.Black40

@Composable
fun CustomDialog(
    onDissmiss: () -> Unit,
    dialogState: Boolean,
    url: String
) {
    val showDialog = remember { mutableStateOf(dialogState) }
    val lifecycleOwner = LocalLifecycleOwner.current


    if (showDialog.value) {
        Dialog(
            onDismissRequest = { onDissmiss.invoke() },
            properties = DialogProperties(dismissOnClickOutside = true, dismissOnBackPress = true)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.65f),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(
                    containerColor = Black40
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    YoutubePlayer(youtubeVideoId = url, lifecycleOwner = lifecycleOwner)
                    Spacer(modifier = Modifier.height(24.dp))
                    TextButton(
                        onClick = {
                            onDissmiss.invoke()
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(stringResource(id = R.string.close_label))
                    }
                }
            }
        }
    }
}

@Composable
fun ComposableWebView(url: String) {
    var webView: WebView? = null

    AndroidView(
        modifier = Modifier
            .fillMaxHeight(0.70F)
            .fillMaxWidth(), factory = {
            WebView(it).apply {
                webViewClient = WebViewClient()
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                settings.javaScriptEnabled = true
                loadUrl(url)
                webView = this
                setOnTouchListener { _, _ -> true }


            }
        }, update = {
            webView = it
            it.loadUrl(url)
        })
}