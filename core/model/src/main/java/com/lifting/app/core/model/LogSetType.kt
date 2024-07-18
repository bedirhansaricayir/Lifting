package com.lifting.app.core.model

/**
 * Created by bedirhansaricayir on 18.07.2024
 */

enum class LogSetType(val value: String, val readableName: String) {
    NORMAL("normal", "Normal"),
    WARM_UP("warm_up", " Warm Up"),
    DROP_SET("drop_set", "Drop Set"),
    FAILURE("failure", "Failure");

    companion object {
        fun fromString(value: String): LogSetType {
            return entries.find { it.value == value } ?: NORMAL
        }
    }
}