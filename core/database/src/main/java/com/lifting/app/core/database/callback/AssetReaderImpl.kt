package com.lifting.app.core.database.callback

import android.content.Context
import com.lifting.app.core.database.model.BarbellEntity
import com.lifting.app.core.database.model.ExerciseEntity
import com.lifting.app.core.database.model.PlateEntity
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

/**
 * Created by bedirhansaricayir on 13.06.2025
 */

class AssetReaderImpl(private val context: Context) : AssetReader {

    private inline fun <reified T> readFile(
        fileName: String,
        deserializer: DeserializationStrategy<T>
    ): T? {
        val jsonFileString = context.assets?.open(fileName)?.bufferedReader().use { it?.readText() }
        return json.decodeFromString(deserializer, jsonFileString.toString())
    }

    override fun readExercises(): List<ExerciseEntity>? =
        readFile("exercises.json", ListSerializer(ExerciseEntity.serializer()))

    override fun readBarbells(): List<BarbellEntity>? =
        readFile("barbells.json", ListSerializer(BarbellEntity.serializer()))

    override fun readPlates(): List<PlateEntity>? =
        readFile("plates.json", ListSerializer(PlateEntity.serializer()))

}

private val json by lazy {
    Json {
        isLenient = true
        prettyPrint = true
        ignoreUnknownKeys = true
        encodeDefaults = true
    }
}
