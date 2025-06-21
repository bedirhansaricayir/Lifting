package com.lifting.app.core.database.callback

import com.lifting.app.core.database.model.BarbellEntity
import com.lifting.app.core.database.model.ExerciseEntity
import com.lifting.app.core.database.model.PlateEntity

/**
 * Created by bedirhansaricayir on 13.06.2025
 */
interface AssetReader {
    fun readExercises(): List<ExerciseEntity>?
    fun readBarbells(): List<BarbellEntity>?
    fun readPlates(): List<PlateEntity>?
}