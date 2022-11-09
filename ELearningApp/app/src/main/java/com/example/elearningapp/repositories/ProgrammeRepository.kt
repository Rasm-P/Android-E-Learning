package com.example.elearningapp.repositories

import com.example.elearningapp.common.ActionState
import com.example.elearningapp.datasource.ProgrammeData
import com.example.elearningapp.repositories.interfaces.ProgrammeRepositoryInterface
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProgrammeRepository @Inject internal constructor() : ProgrammeRepositoryInterface {

    // Using a fake API call to simulate interaction with a real serverside API
    override suspend fun fetchProgrammes() = flow {
        emit(ActionState.Loading)
        //API call
        delay(300)
        val result = ProgrammeData.programmes
        emit(ActionState.Success(result))
    }

}