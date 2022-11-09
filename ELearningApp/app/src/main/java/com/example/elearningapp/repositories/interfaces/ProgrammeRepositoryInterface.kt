package com.example.elearningapp.repositories.interfaces

import com.example.elearningapp.common.ActionState
import com.example.elearningapp.models.Programme
import kotlinx.coroutines.flow.Flow

interface ProgrammeRepositoryInterface {

    suspend fun fetchProgrammes(): Flow<ActionState<List<Programme>>>

}