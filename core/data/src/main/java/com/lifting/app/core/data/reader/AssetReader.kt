package com.lifting.app.core.data.reader

import com.lifting.app.core.model.Exercise

/**
 * Created by bedirhansaricayir on 13.06.2025
 */
interface AssetReader {
    fun readExercises(): List<Exercise>?
}