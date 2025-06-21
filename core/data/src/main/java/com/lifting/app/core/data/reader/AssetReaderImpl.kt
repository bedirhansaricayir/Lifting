package com.lifting.app.core.data.reader

import android.content.Context
import com.lifting.app.core.model.Exercise
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 13.06.2025
 */

class AssetReaderImpl @Inject constructor(private val context: Context?, private val json: Json): AssetReader {

    private inline fun <reified T> readFile(fileName: String, deserializer: DeserializationStrategy<T>): T? {
        val jsonFileString = context?.assets?.open(fileName)?.bufferedReader().use { it?.readText() }
        return json.decodeFromString(deserializer, jsonFileString.toString())
    }

    override fun readExercises(): List<Exercise>? = TODO()
}