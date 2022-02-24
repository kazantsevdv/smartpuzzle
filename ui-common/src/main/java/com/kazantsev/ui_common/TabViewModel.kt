package com.kazantsev.ui_common

import androidx.lifecycle.ViewModel
import com.kazantsev.domain.use_case.FavoriteCountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TabViewModel @Inject constructor(
   val favoriteCountUseCase: FavoriteCountUseCase
) : ViewModel() {

}