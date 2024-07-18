package com.lifting.app.core.database.util

import androidx.room.TypeConverter
import com.lifting.app.core.model.ExerciseCategory
import com.lifting.app.core.model.LogSetType
import com.lifting.app.core.model.PersonalRecord
import com.lifting.app.core.model.parseToExerciseCategory
import com.lifting.app.core.model.toCommaSpString
import java.text.ParseException
import java.time.LocalDateTime
import java.util.Date

/**
 * Created by bedirhansaricayir on 18.07.2024
 */

internal object Converters {

    @TypeConverter
    @JvmStatic
    fun toExerciseCategory(value: String): ExerciseCategory {
        return value.parseToExerciseCategory()

    }

    @TypeConverter
    @JvmStatic
    fun fromExerciseCategory(category: ExerciseCategory?): String? {
        return category?.tag
    }
    

    @TypeConverter
    @JvmStatic
    fun toSetType(value: String): LogSetType {
        return LogSetType.fromString(value)

    }

    @TypeConverter
    @JvmStatic
    fun fromLogSetType(value: LogSetType?): String {
        return value?.value ?: LogSetType.NORMAL.value
    }


    @TypeConverter
    fun toDate(value: Long?): Date? {
        return if (value != null) {
            try {
                return Date(value)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            null
        } else {
            null
        }
    }

    @TypeConverter
    fun fromDate(value: Date?): Long? {
        return value?.time
    }

    @TypeConverter
    fun toLocalDateTime(value: Long?): LocalDateTime? {
        return if (value != null) {
            try {
                return value.toLocalDateTime()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            null
        } else {
            null
        }
    }

    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime?): Long? {
        return value?.toEpochMillis()
    }

    @TypeConverter
    fun fromListOfPersonalRecords(list: List<PersonalRecord>?): String? {
        return list?.toCommaSpString()
    }

    @TypeConverter
    fun toListOfPersonalRecords(str: String?): List<PersonalRecord>? {
        return if (str != null) PersonalRecord.fromCommaSpString(str) else null
    }
}