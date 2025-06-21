package com.lifting.app.core.model

import java.time.LocalDateTime

/**
 * Created by bedirhansaricayir on 09.02.2025
 */

data class Barbell(
    val id: String,
    val name: String,
    val weightKg: Double?,
    val weightLbs: Double?,
    val isActive: Boolean?,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)