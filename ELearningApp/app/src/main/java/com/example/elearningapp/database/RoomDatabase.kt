package com.example.elearningapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.elearningapp.common.DatabaseTypeConverters
import com.example.elearningapp.database.dao.NoteDao
import com.example.elearningapp.models.entities.NoteEntity

@Database(
    entities = [NoteEntity::class],
    exportSchema = true,
    autoMigrations = [],
    version = 1)
@TypeConverters(DatabaseTypeConverters::class)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}