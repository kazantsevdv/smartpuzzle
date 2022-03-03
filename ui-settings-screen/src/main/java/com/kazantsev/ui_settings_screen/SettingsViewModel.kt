package com.kazantsev.ui_settings_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kazantsev.domain.model.Difficult
import com.kazantsev.domain.model.Puzzle
import com.kazantsev.domain.use_case.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val clearSolvedUseCase: ClearSolvedUseCase,
    private val difficultSaveUseCase: DifficultSaveUseCase,
    val preferenceFlowUseCase: PreferenceFlowUseCase,
    private val notShowSolvedSaveUseCase: NotShowSolvedSaveUseCase,
    ) : ViewModel() {


    fun setFilter(difficult: Difficult) {
        viewModelScope.launch {
            difficultSaveUseCase(difficult)
        }
    }

    fun setShowSolved(show: Boolean) {
        viewModelScope.launch {
            notShowSolvedSaveUseCase(show)
        }
    }

    fun clearSolved() {
        viewModelScope.launch {
            clearSolvedUseCase()
        }
    }
}