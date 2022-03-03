package com.kazantsev.domain.use_case

import com.kazantsev.domain.model.Category
import com.kazantsev.domain.model.Difficult
import com.kazantsev.domain.repository.DataStore
import com.kazantsev.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DifficultFlowUseCase @Inject constructor(
    private val dataStore: DataStore,
) {
    operator fun invoke(): Flow<Difficult> {
        return dataStore.difficultMode.map { Difficult.valueOf(it)}
    }
}