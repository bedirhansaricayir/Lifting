package com.lifting.app.core.model

import java.time.LocalDateTime

/**
 * Created by bedirhansaricayir on 15.07.2024
 */

data class Workout(
    val id: String,
    val name: String?,
    val note: String?,
    val inProgress: Boolean?,
    val isHidden: Boolean?,
    var startAt: LocalDateTime?,
    var completedAt: LocalDateTime?,
    val personalRecords: List<PersonalRecord>?,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
    val duration: Long?
)
