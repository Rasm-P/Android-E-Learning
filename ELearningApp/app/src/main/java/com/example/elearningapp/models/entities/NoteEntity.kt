package com.example.elearningapp.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName = "note")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String = "",
    val text: String = "",
    val lastEdited: OffsetDateTime = OffsetDateTime.now()
)