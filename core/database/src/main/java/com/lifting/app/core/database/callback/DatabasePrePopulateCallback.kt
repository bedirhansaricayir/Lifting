package com.lifting.app.core.database.callback

import android.content.Context
import android.util.Log.e
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.lifting.app.core.database.dao.BarbellsDao
import com.lifting.app.core.database.dao.ExercisesDao
import com.lifting.app.core.database.dao.PlatesDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Provider

/**
 * Created by bedirhansaricayir on 14.06.2025
 */

internal class DatabasePrePopulateCallback(
    private val context: Context,
    private val exercisesProvider: Provider<ExercisesDao>,
    private val barbellsProvider: Provider<BarbellsDao>,
    private val platesProvider: Provider<PlatesDao>,
) : RoomDatabase.Callback() {

    val assetReader by lazy {
        AssetReaderImpl(context)
    }

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            prepopulateBarbells(barbellsProvider.get())
            prepopulatePlates(platesProvider.get())
            prepopulateExercises(exercisesProvider.get())
        }
    }

    suspend fun prepopulateBarbells(barbellsDao: BarbellsDao) {
        try {
            assetReader.readBarbells()?.let { barbells ->
                barbellsDao.insertBarbells(barbells)
                e(TAG, "successfully pre-populated users into database")
            }
        } catch (e: Exception) {
            e(TAG, "failed to pre-populate users into database")
        }
    }

    suspend fun prepopulatePlates(platesDao: PlatesDao) {
        try {
            assetReader.readPlates()?.let { plates ->
                platesDao.insertPlates(plates)
                e(TAG, "successfully pre-populated users into database")
            }
        } catch (e: Exception) {
            e(TAG, "failed to pre-populate users into database")
        }
    }


    suspend fun prepopulateExercises(exercisesDao: ExercisesDao) {
        try {
            assetReader.readExercises()?.let { exercises ->
                exercisesDao.insertExercises(exercises)
                e(TAG, "successfully pre-populated users into database")
            }
        } catch (e: Exception) {
            e(TAG, "failed to pre-populate users into database: ${e.message}")
        }
    }

    companion object {
        const val TAG = "DatabasePrePopulateCallback"
    }
}


