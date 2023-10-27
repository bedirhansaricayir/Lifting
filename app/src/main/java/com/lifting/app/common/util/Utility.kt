package com.lifting.app.common.util

import android.content.Context
import android.content.Intent
import android.net.Uri

object Utility {

    fun startBrowserIntent(context: Context, url: String){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }
}