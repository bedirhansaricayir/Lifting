package com.lifting.app.core.ui.common

import android.app.LocaleManager
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.lifting.app.core.model.AppLanguage

/**
 * Created by bedirhansaricayir on 09.06.2025
 */

object AppLocale {
    fun changeLocale(appLanguage: AppLanguage, context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java).applicationLocales =
                LocaleList.forLanguageTags(appLanguage.code)
        } else {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(appLanguage.code))
        }
    }

    fun getLocaleCode(context: Context): String {
        val appLocale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java)
                .applicationLocales[0]
        } else {
            AppCompatDelegate.getApplicationLocales()[0]
        }

        return if (appLocale != null) {
            appLocale.toLanguageTag().split("-").first()
        } else {
            Resources.getSystem().configuration.locales[0].language
        }
    }
}