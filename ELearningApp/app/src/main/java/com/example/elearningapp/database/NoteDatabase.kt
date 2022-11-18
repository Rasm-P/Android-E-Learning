package com.example.elearningapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.elearningapp.common.DatabaseTypeConverters
import com.example.elearningapp.database.dao.NoteDao
import com.example.elearningapp.models.entities.NoteEntity

//Built with inspiration from: https://github.com/HenrikPihl/retrofit_room/blob/feature/add-search/app/src/main/java/io/shortcut/dtucourceretrofit_room/datasource/AppDatabase.kt

@Database(
    entities = [NoteEntity::class],
    autoMigrations = [],
    exportSchema = true,
    version = 1)
@TypeConverters(DatabaseTypeConverters::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}