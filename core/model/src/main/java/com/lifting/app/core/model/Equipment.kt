package com.lifting.app.core.model

/**
 * Created by bedirhansaricayir on 16.06.2025
 */


enum class Equipment(
    val id: String,
) {
    BARBELL("barbell"),
    BAND("band"),
    BODYWEIGHT("bodyweight"),
    CABLE("cable"),
    DUMBBELL("dumbbell"),
    MACHINE("machine"),
    KETTLEBELL("kettlebell"),
    SLAM_BALL("slam_ball"),
    OTHER("other");

    companion object {
        fun fromId(id: String): Equipment = Equipment.entries.find { it.id == id } ?: OTHER
        fun toId(equipment: Equipment): String = equipment.id
    }
}