package com.lifting.app.feature_home.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lifting.app.feature_home.data.local.entity.AnalysisDataEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.util.Date

@Dao
interface AnalysisDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnalysisData(analysisDataEntity: AnalysisDataEntity)

    @Query("SELECT * FROM analysis_data_table WHERE date_column BETWEEN :currentDate AND :endDate")
     fun getAnalysisDataWhereTimeRange(currentDate: LocalDate,endDate: LocalDate): Flow<List<AnalysisDataEntity>>
}