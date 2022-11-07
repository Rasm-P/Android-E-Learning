package com.example.elearningapp.database.dao

import androidx.room.*
import com.example.elearningapp.models.entities.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert
    suspend fun insertNote(note: NoteEntity)

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Query("SELECT * FROM note")
    fun getAllNotes(): Flow<List<NoteEntity>>
}