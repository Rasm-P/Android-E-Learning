package com.example.elearningapp.di

import android.content.Context
import androidx.room.Room
import com.example.elearningapp.common.DatabaseTypeConverters
import com.example.elearningapp.database.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): RoomDatabase {
        DatabaseTypeConverters
        return Room.databaseBuilder(
            context,
            RoomDatabase::class.java,
            "note.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideNoteDao(roomDatabase: RoomDatabase) = roomDatabase.noteDao()

}