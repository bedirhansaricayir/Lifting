package com.lifting.app.feature_home.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lifting.app.feature_home.data.local.entity.AnalysisDataEntity
import com.lifting.app.feature_home.data.local.entity.AnalysisDataTypeConverter

@Database(entities = [AnalysisDataEntity::class], version = 1, exportSchema = false)
@TypeConverters(AnalysisDataTypeConverter::class)
abstract class AnalysisDatabase: RoomDatabase() {
    abstract fun analysisDao(): AnalysisDao
}