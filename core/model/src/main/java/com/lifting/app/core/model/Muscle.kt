package com.lifting.app.core.model

import com.lifting.app.core.model.Muscle.entries


/**
 * Created by bedirhansaricayir on 15.07.2024
 */

enum class Muscle(
    val tag: String,
) {
    ABS("abs"),
    ADDUCTORS("adductors"),
    BACK("back"),
    BICEPS("biceps"),
    CALVES("calves"),
    CARDIO("cardio"),
    CHEST("chest"),
    CORE("core"),
    FOREARMS("forearms"),
    FULL_BODY("full_body"),
    GLUTES("glutes"),
    HAMSTRINGS("hamstrings"),
    LATS("lats"),
    LOWER_BACK("lower_back"),
    WEIGHTLIFTING("weightlifting"),
    QUADRICEPS("quadriceps"),
    SHOULDERS("shoulders"),
    TRAPS("traps"),
    TRICEPS("triceps"),
    UPPER_BACK("upper_back"),;

    companion object {
        fun fromTag(tag: String): Muscle? {
            return entries.find { it.tag == tag.lowercase() }
        }
    }
}