package com.example.elearningapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.elearningapp.models.entities.NoteEntity
import com.example.elearningapp.repositories.interfaces.NoteRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


@HiltViewModel
class NoteViewModel @Inject internal constructor(private val _noteRepository: NoteRepositoryInterface): ViewModel() {

    suspend fun insertNote(note: NoteEntity) {
        _noteRepository.insertNote(note)
    }

    suspend fun updateNote(note: NoteEntity) {
        _noteRepository.updateNote(note)
    }

    suspend fun deleteNote(note: NoteEntity) {
        _noteRepository.deleteNote(note)
    }

    fun getAllNotes(): Flow<List<NoteEntity>> = flow {
        _noteRepository.getAllNotes()
    }
}