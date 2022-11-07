package com.example.elearningapp.viewmodels

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elearningapp.models.entities.NoteEntity
import com.example.elearningapp.repositories.interfaces.NoteRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NoteViewModel @Inject internal constructor(private val _noteRepository: NoteRepositoryInterface): ViewModel() {

    val allNotesState by lazy {
        _noteRepository.getAllNotes()
        .stateIn(viewModelScope , started = SharingStarted.WhileSubscribed(), emptyList())
    }

    suspend fun insertNote(note: NoteEntity) {
        _noteRepository.insertNote(note)
    }

    suspend fun updateNote(note: NoteEntity) {
        _noteRepository.updateNote(note)
    }

    suspend fun deleteNote(note: NoteEntity) {
        _noteRepository.deleteNote(note)
    }
}