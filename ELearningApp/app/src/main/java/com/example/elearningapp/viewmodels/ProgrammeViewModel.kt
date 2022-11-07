package com.example.elearningapp.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elearningapp.common.ActionState
import com.example.elearningapp.models.Programme
import com.example.elearningapp.repositories.interfaces.ProgrammeRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProgrammeViewModel @Inject internal constructor(private val _programmeRepository: ProgrammeRepositoryInterface): ViewModel() {

    private val _programmeState = mutableStateOf<ActionState<List<Programme>>>(ActionState.Initial)
    val programmeState: State<ActionState<List<Programme>>> = _programmeState

    fun fetchProgrammes() {
        viewModelScope.launch {
            _programmeRepository.fetchProgrammes().collect {
                    response -> _programmeState.value = response
            }
        }
    }

    fun resetProgrammeActionState() {
        _programmeState.value = ActionState.Initial
    }

}