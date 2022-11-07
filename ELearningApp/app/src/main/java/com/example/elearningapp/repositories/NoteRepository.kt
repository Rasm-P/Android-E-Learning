package com.example.elearningapp.repositories

import com.example.elearningapp.database.dao.NoteDao
import com.example.elearningapp.models.entities.NoteEntity
import com.example.elearningapp.repositories.interfaces.NoteRepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NoteRepository @Inject internal constructor(private var noteDao: NoteDao) : NoteRepositoryInterface{

    override suspend fun insertNote(note: NoteEntity) {
        noteDao.insertNote(note)
    }

    override suspend fun updateNote(note: NoteEntity) {
        noteDao.updateNote(note)
    }

    override suspend fun deleteNote(note: NoteEntity) {
        noteDao.deleteNote(note)
    }

    override fun getAllNotes(): Flow<List<NoteEntity>> = flow {
        noteDao.getAllNotes()
    }
}