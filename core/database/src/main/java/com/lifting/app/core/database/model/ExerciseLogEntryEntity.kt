package com.lifting.app.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lifting.app.core.model.LogSetType
import com.lifting.app.core.model.PersonalRecord
import java.time.LocalDateTime

/**
 * Created by bedirhansaricayir on 18.07.2024
 */

@Entity(tableName = "exercise_log_entries")
data class ExerciseLogEntryEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "entry_id")
    val entryId: String,

    @ColumnInfo(name = "log_id")
    var logId: String? = null,
    @ColumnInfo(name = "junction_id")
    var junctionId: String? = null,

    // Number of set
    @ColumnInfo(name = "set_number")
    var setNumber: Int? = null,
    @ColumnInfo(name = "set_type")
    var setType: LogSetType? = null,

    @ColumnInfo(name = "weight")
    var weight: Double? = null,
    @ColumnInfo(name = "reps")
    var reps: Int? = null,
    @ColumnInfo(name = "rpe")
    var rpe: Float? = null,

    @ColumnInfo(name = "completed")
    var completed: Boolean = false,

    // Time in milliseconds
    @ColumnInfo(name = "time_recorded")
    var timeRecorded: Long? = null,

    @ColumnInfo(name = "distance")
    var distance: Double? = null,

    @ColumnInfo(name = "weight_unit")
    var weight_unit: String? = null,
    @ColumnInfo(name = "distance_unit")
    var distance_unit: String? = null,

    @ColumnInfo(name = "personal_records")
    var personalRecords: List<PersonalRecord>? = null,

    @ColumnInfo(name = "created_at")
    var createdAt: LocalDateTime? = null,
    @ColumnInfo(name = "update_at")
    var updatedAt: LocalDateTime? = null,
) {
    companion object {
        fun List<ExerciseLogEntryEntity>.calculateTotalVolume(): Double {
            return this.sumOf { entry -> (entry.weight ?: 0.0) * (entry.reps ?: 0) }
        }

        fun List<ExerciseLogEntryEntity>.getTotalPRs(workoutPrs: Int? = null): Int {
            return sumOf { it.personalRecords?.size ?: 0 } + (workoutPrs ?: 0)
        }
    }
}

