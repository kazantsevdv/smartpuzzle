package com.kazantsev.domain.repository

import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getNote(): Flow<List<String>>
}