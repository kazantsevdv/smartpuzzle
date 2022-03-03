package com.kazantsev.domain.use_case

import com.kazantsev.domain.model.Difficult
import com.kazantsev.domain.model.Preference
import com.kazantsev.domain.repository.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferenceFlowUseCase @Inject constructor(
    private val dataStore: DataStore,
) {
    operator fun invoke(): Flow<Preference> {
        return dataStore.preferenceMode
    }
}