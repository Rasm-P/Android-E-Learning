package com.example.elearningapp.common

import androidx.room.TypeConverter
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

// Built with inspiration from https://medium.com/androiddevelopers/room-time-2b4cf9672b98

object DatabaseTypeConverters {

    private val dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @TypeConverter
    fun toOffsetDateTime(timeValue: String): OffsetDateTime {
        return dateTimeFormatter.parse(timeValue, OffsetDateTime::from)
    }

    @TypeConverter
    fun fromOffsetDateTime(date: OffsetDateTime): String {
        return date.format(dateTimeFormatter)
    }
}