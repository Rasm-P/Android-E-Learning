package com.example.elearningapp.repositories.interfaces

import com.example.elearningapp.models.entities.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NoteRepositoryInterface {

    fun getAllNotes(): Flow<List<NoteEntity>>

    suspend fun insertNote(note: NoteEntity)

    suspend fun updateNote(note: NoteEntity)

    suspend fun deleteNote(id: Long)

}