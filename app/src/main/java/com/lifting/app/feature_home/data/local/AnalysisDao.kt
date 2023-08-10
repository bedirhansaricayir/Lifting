package com.lifting.app.feature_home.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lifting.app.common.constants.Constants.Companion.ANALYSIS_DATA_TABLE
import com.lifting.app.common.constants.Constants.Companion.DATE_COLUMN
import com.lifting.app.feature_home.data.local.entity.AnalysisDataEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface AnalysisDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnalysisData(analysisDataEntity: AnalysisDataEntity)

    @Query("SELECT * FROM $ANALYSIS_DATA_TABLE")
    fun getAllAnalysisData(): Flow<List<AnalysisDataEntity>>

    @Query("SELECT * FROM $ANALYSIS_DATA_TABLE WHERE $DATE_COLUMN BETWEEN :startDate AND :endDate")
    fun getAnalysisDataWhereTimeRange(startDate: LocalDate,endDate: LocalDate): Flow<List<AnalysisDataEntity>>
}