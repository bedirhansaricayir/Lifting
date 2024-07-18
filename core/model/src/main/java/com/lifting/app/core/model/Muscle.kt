package com.lifting.app.core.model

/**
 * Created by bedirhansaricayir on 15.07.2024
 */

data class Muscle(
    val tag: String,
    val name: String,
    val type: String?,
    val isDeletable: Boolean?,
    val isHidden: Boolean?
)
