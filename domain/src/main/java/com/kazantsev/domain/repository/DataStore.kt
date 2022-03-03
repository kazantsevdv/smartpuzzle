package com.kazantsev.domain.repository

import com.kazantsev.domain.model.Preference
import kotlinx.coroutines.flow.Flow

interface DataStore {
    suspend fun setDifficult(difficult: Int)
    suspend fun setNotShowSolved(solved: Boolean)
    val preferenceMode: Flow<Preference>
}