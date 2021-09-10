package org.bl.coffeecounter.db.converters

import androidx.room.TypeConverter
import java.time.LocalDateTime

class Converters {

    @TypeConverter
    fun fromLocalDateString(value: String?): LocalDateTime? {
        return LocalDateTime.parse(value) ?: null
    }

    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime?): String? {
        return value?.toString()
    }

}