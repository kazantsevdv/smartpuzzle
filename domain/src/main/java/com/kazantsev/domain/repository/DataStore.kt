package com.kazantsev.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStore {
    suspend fun setDifficult(difficult: Int)
    val difficultMode: Flow<Int>
}