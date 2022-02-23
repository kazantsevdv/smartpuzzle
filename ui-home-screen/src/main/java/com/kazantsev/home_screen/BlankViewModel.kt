package com.kazantsev.home_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.kazantsev.domain.use_case.BlankUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BlankViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val blankUseCase: BlankUseCase,
) : ViewModel() {
}