package com.lifting.app.core.util

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable

@Composable
fun ShowToast(context: Context, message: String) {
    Toast.makeText(context,message,Toast.LENGTH_LONG).show()
}