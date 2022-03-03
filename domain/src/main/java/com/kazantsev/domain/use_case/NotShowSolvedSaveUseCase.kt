package com.kazantsev.domain.use_case

import com.kazantsev.domain.model.Difficult
import com.kazantsev.domain.repository.DataStore
import javax.inject.Inject

class NotShowSolvedSaveUseCase @Inject constructor(
    private val dataStore: DataStore,
) {
    suspend operator fun invoke(solved: Boolean) {
        return dataStore.setNotShowSolved(solved)
    }
}