package com.lifting.app.feature_tracker.data.local.entity

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lifting.app.feature_tracker.data.local.AnalysisDao
import com.lifting.app.feature_tracker.data.local.entity.AnalysisDataEntity
import com.lifting.app.feature_tracker.data.local.entity.AnalysisDataTypeConverter

@Database(entities = [AnalysisDataEntity::class], version = 4, exportSchema = false)
@TypeConverters(AnalysisDataTypeConverter::class)
abstract class AnalysisDatabase: RoomDatabase() {
    abstract fun analysisDao(): AnalysisDao
}