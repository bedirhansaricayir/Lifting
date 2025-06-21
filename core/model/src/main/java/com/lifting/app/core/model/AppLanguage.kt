package com.lifting.app.core.model

import com.lifting.app.core.model.AppLanguage.entries


/**
 * Created by bedirhansaricayir on 07.06.2025
 */
enum class AppLanguage(val code: String) {
    English(code = "en"),
    Turkish(code = "tr"),
    ;

    companion object {
        fun getLanguage(code: String) = entries.firstOrNull { it.code == code } ?: English
    }
}