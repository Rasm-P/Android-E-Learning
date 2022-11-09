package com.example.elearningapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elearningapp.models.entities.NoteEntity
import com.example.elearningapp.repositories.interfaces.NoteRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import javax.inject.Inject


@HiltViewModel
class NoteViewModel @Inject internal constructor(private val _noteRepository: NoteRepositoryInterface): ViewModel() {

    val allNotesState by lazy {
        _noteRepository.getAllNotes()
        .stateIn(viewModelScope , started = SharingStarted.WhileSubscribed(), emptyList())
    }

    fun insertNote(title: String, noteText: String) = viewModelScope.launch {
        _noteRepository.insertNote(NoteEntity(title = title, text = noteText))
    }

    fun updateNote(id: Long, title: String, noteText: String) = viewModelScope.launch {
        _noteRepository.updateNote(NoteEntity(id = id, title = title, text = noteText, lastEdited = OffsetDateTime.now()))
    }

    fun deleteNote(id: Long) = viewModelScope.launch {
        _noteRepository.deleteNote(id)
    }
}